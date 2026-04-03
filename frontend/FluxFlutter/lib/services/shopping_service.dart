import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/shopping_receipt.dart';
import '../models/product.dart';
import '../models/shopping_policy.dart';
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
      // Try real API first
      final walletAddress = walletService.address ?? '';
      final requestBody = jsonEncode({
        'budget': budget,
        'purpose': purpose,
        'currency': currency,
        'walletAddress': walletAddress,
      });

      for (final baseUrl in _baseUrls) {
        try {
          final response = await http
              .post(
                Uri.parse('$baseUrl/shopping/checkout'),
                headers: {'Content-Type': 'application/json'},
                body: requestBody,
              )
              .timeout(const Duration(seconds: 5));

          if (response.statusCode == 200) {
            final Map<String, dynamic> data = jsonDecode(response.body);
            return ShoppingReceipt.fromJson(data);
          }
        } catch (e) {
          // Continue to next URL
        }
      }

      // If API unavailable, return mock data
      return _generateMockReceipt(budget, purpose, currency);
    } catch (e) {
      throw Exception('Checkout failed: $e');
    }
  }

  // Generate mock receipt for demo when backend is unavailable
  ShoppingReceipt _generateMockReceipt(
    double budget,
    String purpose,
    String currency,
  ) {
    final mockProducts = _getMockProductsByPurpose(purpose);
    double totalSpent = 0;
    for (final product in mockProducts) {
      totalSpent += product.price;
    }

    final remaining = budget - totalSpent;

    return ShoppingReceipt(
      receiptId: 'DEMO-${DateTime.now().millisecondsSinceEpoch}',
      selectedProducts: mockProducts,
      totalSpent: totalSpent,
      remainingBudget: remaining > 0 ? remaining : 0,
      createdAt: DateTime.now(),
      policy: ShoppingPolicy(
        policyId: 'DEMO-POLICY',
        agentAddress: walletService.address ?? '0x0',
        purpose: purpose,
        maxBudget: budget,
        currency: currency,
        signature: '0xDEMO',
        expiresAt: DateTime.now().add(const Duration(days: 7)),
      ),
      payments: [],
    );
  }

  List<Product> _getMockProductsByPurpose(String purpose) {
    final mockProducts = {
      'conference': [
        Product(
          id: 1,
          name: 'Professional Blazer',
          category: 'Jacket',
          price: 89.99,
          tags: ['professional', 'business'],
        ),
        Product(
          id: 2,
          name: 'White Dress Shirt',
          category: 'Shirt',
          price: 49.99,
          tags: ['formal', 'white'],
        ),
        Product(
          id: 3,
          name: 'Gray Trousers',
          category: 'Pants',
          price: 59.99,
          tags: ['formal', 'gray'],
        ),
      ],
      'casual': [
        Product(
          id: 4,
          name: 'Cotton T-Shirt',
          category: 'Shirt',
          price: 29.99,
          tags: ['casual', 'cotton'],
        ),
        Product(
          id: 5,
          name: 'Casual Jeans',
          category: 'Pants',
          price: 69.99,
          tags: ['casual', 'denim'],
        ),
      ],
      'formal': [
        Product(
          id: 6,
          name: 'Tuxedo Jacket',
          category: 'Jacket',
          price: 199.99,
          tags: ['formal', 'tuxedo'],
        ),
        Product(
          id: 7,
          name: 'Black Dress Pants',
          category: 'Pants',
          price: 79.99,
          tags: ['formal', 'black'],
        ),
      ],
      'sport': [
        Product(
          id: 8,
          name: 'Athletic T-Shirt',
          category: 'Shirt',
          price: 39.99,
          tags: ['sport', 'athletic'],
        ),
        Product(
          id: 9,
          name: 'Sport Shorts',
          category: 'Shorts',
          price: 44.99,
          tags: ['sport', 'shorts'],
        ),
        Product(
          id: 10,
          name: 'Running Shoes',
          category: 'Shoes',
          price: 129.99,
          tags: ['sport', 'shoes'],
        ),
      ],
    };

    return mockProducts[purpose] ?? mockProducts['casual'] ?? [];
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
