class ViewHistoryItem {
  final int productId;
  final String productName;
  final String category;
  final String price;
  final String currency;
  final List<String> tags;
  final String purpose;
  final String viewedAt;

  ViewHistoryItem({
    required this.productId,
    required this.productName,
    required this.category,
    required this.price,
    required this.currency,
    required this.tags,
    required this.purpose,
    required this.viewedAt,
  });

  factory ViewHistoryItem.fromJson(Map<String, dynamic> json) {
    return ViewHistoryItem(
      productId: json['productId'] as int,
      productName: json['productName'] as String,
      category: json['category'] as String,
      price: json['price'] as String,
      currency: json['currency'] as String? ?? 'USDC',
      tags:
          (json['tags'] as List<dynamic>?)?.map((e) => e as String).toList() ??
          [],
      purpose: json['purpose'] as String? ?? 'Not specified',
      viewedAt: json['viewedAt'] as String? ?? '',
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'productId': productId,
      'productName': productName,
      'category': category,
      'price': price,
      'currency': currency,
      'tags': tags,
      'purpose': purpose,
      'viewedAt': viewedAt,
    };
  }
}

class UserProfile {
  final String walletAddress;
  final String displayName;
  final String email;
  final String createdAt;
  final String updatedAt;
  final int totalPurchases;
  final double totalSpent;
  final List<ViewHistoryItem> viewHistory;

  UserProfile({
    required this.walletAddress,
    required this.displayName,
    required this.email,
    required this.createdAt,
    required this.updatedAt,
    required this.totalPurchases,
    required this.totalSpent,
    required this.viewHistory,
  });

  factory UserProfile.fromJson(Map<String, dynamic> json) {
    return UserProfile(
      walletAddress: json['walletAddress'] as String,
      displayName: json['displayName'] as String? ?? 'User',
      email: json['email'] as String? ?? '',
      createdAt: json['createdAt'] as String? ?? '',
      updatedAt: json['updatedAt'] as String? ?? '',
      totalPurchases: json['totalPurchases'] as int? ?? 0,
      totalSpent: (json['totalSpent'] as num?)?.toDouble() ?? 0.0,
      viewHistory:
          (json['viewHistory'] as List<dynamic>?)
              ?.map((e) => ViewHistoryItem.fromJson(e as Map<String, dynamic>))
              .toList() ??
          [],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'walletAddress': walletAddress,
      'displayName': displayName,
      'email': email,
      'createdAt': createdAt,
      'updatedAt': updatedAt,
      'totalPurchases': totalPurchases,
      'totalSpent': totalSpent,
      'viewHistory': viewHistory.map((e) => e.toJson()).toList(),
    };
  }
}
