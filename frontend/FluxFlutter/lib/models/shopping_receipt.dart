import 'product.dart';
import 'shopping_policy.dart';
import 'payment_result.dart';

class ShoppingReceipt {
  final String receiptId;
  final ShoppingPolicy policy;
  final List<Product> selectedProducts;
  final List<PaymentResult> payments;
  final double totalSpent;
  final double remainingBudget;
  final DateTime createdAt;

  ShoppingReceipt({
    required this.receiptId,
    required this.policy,
    required this.selectedProducts,
    required this.payments,
    required this.totalSpent,
    required this.remainingBudget,
    required this.createdAt,
  });

  factory ShoppingReceipt.fromJson(Map<String, dynamic> json) {
    return ShoppingReceipt(
      receiptId: json['receiptId'] as String,
      policy: ShoppingPolicy.fromJson(json['policy'] as Map<String, dynamic>),
      selectedProducts: (json['selectedProducts'] as List<dynamic>)
          .map((e) => Product.fromJson(e as Map<String, dynamic>))
          .toList(),
      payments: (json['payments'] as List<dynamic>)
          .map((e) => PaymentResult.fromJson(e as Map<String, dynamic>))
          .toList(),
      totalSpent: (json['totalSpent'] as num).toDouble(),
      remainingBudget: (json['remainingBudget'] as num).toDouble(),
      createdAt: DateTime.parse(json['createdAt'] as String),
    );
  }
}
