import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/shopping_receipt.dart';
import 'wallet_service.dart';

class ShoppingService {
  static const String _host = 'http://localhost:8080';
  static const List<String> _baseUrls = <String>['$_host/api', _host];

  Future<ShoppingReceipt> checkout({
    required double budget,
    required String purpose,
    String currency = 'USDC',
  }) async {
    try {
      // Get wallet address
      final walletAddress = walletService.address ?? '';
      final requestBody = jsonEncode({
        'budget': budget,
        'purpose': purpose,
        'currency': currency,
        'walletAddress': walletAddress,
      });

      String? lastError;

      for (final baseUrl in _baseUrls) {
        final response = await http.post(
          Uri.parse('$baseUrl/shopping/checkout'),
          headers: {'Content-Type': 'application/json'},
          body: requestBody,
        );

        if (response.statusCode == 200) {
          final Map<String, dynamic> data = jsonDecode(response.body);
          return ShoppingReceipt.fromJson(data);
        }

        if (response.statusCode != 404) {
          throw Exception(
            'Failed to checkout (${response.statusCode}): ${response.body}',
          );
        }

        lastError = 'Endpoint not found at $baseUrl/shopping/checkout';
      }

      throw Exception(lastError ?? 'No compatible checkout endpoint found');
    } catch (e) {
      throw Exception('Checkout failed: $e');
    }
  }

  Future<bool> checkHealth() async {
    try {
      for (final baseUrl in _baseUrls) {
        final response = await http.get(Uri.parse('$baseUrl/shopping/health'));
        if (response.statusCode == 200) {
          return true;
        }

        // Try next base URL only when endpoint does not exist.
        if (response.statusCode != 404) {
          return false;
        }
      }

      return false;
    } catch (e) {
      return false;
    }
  }
}
