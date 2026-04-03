class PaymentResult {
  final int productId;
  final String supplierId;
  final double amount;
  final bool success;
  final String transactionHash;
  final DateTime timestamp;

  PaymentResult({
    required this.productId,
    required this.supplierId,
    required this.amount,
    required this.success,
    required this.transactionHash,
    required this.timestamp,
  });

  factory PaymentResult.fromJson(Map<String, dynamic> json) {
    return PaymentResult(
      productId: json['productId'] as int,
      supplierId: json['supplierCategory'] as String? ?? json['supplierId'] as String? ?? 'unknown',
      amount: (json['amount'] as num).toDouble(),
      success: json['success'] as bool,
      transactionHash: json['txHash'] as String? ?? json['transactionHash'] as String? ?? '0x0',
      timestamp: json['timestamp'] != null 
          ? DateTime.parse(json['timestamp'] as String)
          : DateTime.now(),
    );
  }
}
