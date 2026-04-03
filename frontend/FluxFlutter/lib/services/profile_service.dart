import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/user_profile.dart';
import 'wallet_service.dart';

class ProfileService {
  static const String _host = 'http://localhost:8080';
  static const List<String> _baseUrls = <String>['$_host/api', _host];

  Future<UserProfile> getUserProfile() async {
    try {
      final walletAddress = walletService.address ?? '';
      if (walletAddress.isEmpty) {
        throw Exception('Wallet bağlı değil');
      }

      String? lastError;

      for (final baseUrl in _baseUrls) {
        try {
          final response = await http
              .get(
                Uri.parse('$baseUrl/users/profile/$walletAddress'),
                headers: {'Content-Type': 'application/json'},
              )
              .timeout(const Duration(seconds: 5));

          if (response.statusCode == 200) {
            final Map<String, dynamic> data = jsonDecode(response.body);
            return UserProfile.fromJson(data);
          }

          if (response.statusCode != 404) {
            lastError = 'Profile endpoint error (${response.statusCode})';
          }
        } catch (e) {
          lastError = 'Connection error: $baseUrl';
        }
      }

      // If backend is unavailable, create default profile with wallet address
      final shortAddr = walletAddress.length >= 6
          ? walletAddress.substring(0, 6)
          : walletAddress;
      return UserProfile(
        walletAddress: walletAddress,
        displayName: 'Kullanıcı $shortAddr',
        email: '',
        createdAt: DateTime.now().toIso8601String(),
        updatedAt: DateTime.now().toIso8601String(),
        totalPurchases: 0,
        totalSpent: 0.0,
        viewHistory: [],
      );
    } catch (e) {
      // Return minimal profile on any error
      final addr = walletService.address ?? 'unknown';
      return UserProfile(
        walletAddress: addr,
        displayName: 'Kullanıcı',
        email: '',
        createdAt: DateTime.now().toIso8601String(),
        updatedAt: DateTime.now().toIso8601String(),
        totalPurchases: 0,
        totalSpent: 0.0,
        viewHistory: [],
      );
    }
  }

  Future<List<ViewHistoryItem>> getViewHistory({int limit = 20}) async {
    try {
      final walletAddress = walletService.address ?? '';
      if (walletAddress.isEmpty) {
        return [];
      }

      for (final baseUrl in _baseUrls) {
        try {
          final response = await http
              .get(
                Uri.parse('$baseUrl/users/$walletAddress/history?limit=$limit'),
                headers: {'Content-Type': 'application/json'},
              )
              .timeout(const Duration(seconds: 5));

          if (response.statusCode == 200) {
            final Map<String, dynamic> data = jsonDecode(response.body);
            final List<dynamic> historyList = data['viewHistory'] ?? [];
            return historyList
                .map((e) => ViewHistoryItem.fromJson(e as Map<String, dynamic>))
                .toList();
          }
        } catch (e) {
          // Try next endpoint
          continue;
        }
      }

      // Return empty list if backend unavailable
      return [];
    } catch (e) {
      return [];
    }
  }

  Future<void> addToViewHistory({
    required int productId,
    required String productName,
    required String category,
    required String price,
    required String currency,
    required List<String> tags,
    required String purpose,
  }) async {
    try {
      final walletAddress = walletService.address ?? '';
      if (walletAddress.isEmpty) {
        throw Exception('Wallet not connected');
      }

      final requestBody = jsonEncode({
        'productId': productId,
        'productName': productName,
        'category': category,
        'price': price,
        'currency': currency,
        'tags': tags,
        'purpose': purpose,
      });

      for (final baseUrl in _baseUrls) {
        final response = await http.post(
          Uri.parse('$baseUrl/users/$walletAddress/history'),
          headers: {'Content-Type': 'application/json'},
          body: requestBody,
        );

        if (response.statusCode == 200) {
          return;
        }

        if (response.statusCode != 404) {
          throw Exception(
            'Failed to add to history (${response.statusCode}): ${response.body}',
          );
        }
      }

      throw Exception('No compatible history endpoint found');
    } catch (e) {
      // Silently fail if history tracking is not available
      print('Warning: Could not add to history: $e');
    }
  }

  Future<Map<String, dynamic>> getUserStats() async {
    try {
      final walletAddress = walletService.address ?? '';
      if (walletAddress.isEmpty) {
        return {};
      }

      for (final baseUrl in _baseUrls) {
        try {
          final response = await http
              .get(
                Uri.parse('$baseUrl/users/$walletAddress/stats'),
                headers: {'Content-Type': 'application/json'},
              )
              .timeout(const Duration(seconds: 5));

          if (response.statusCode == 200) {
            return jsonDecode(response.body) as Map<String, dynamic>;
          }
        } catch (e) {
          continue;
        }
      }

      // Return default stats if backend unavailable
      return {'totalPurchases': 0, 'totalSpent': 0.0, 'totalViews': 0};
    } catch (e) {
      return {'totalPurchases': 0, 'totalSpent': 0.0, 'totalViews': 0};
    }
  }
}

final profileService = ProfileService();
