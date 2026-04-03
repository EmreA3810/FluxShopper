class ShoppingPolicy {
  final String policyId;
  final double maxBudget;
  final String currency;
  final String purpose;
  final String agentAddress;
  final String signature;
  final DateTime expiresAt;

  ShoppingPolicy({
    required this.policyId,
    required this.maxBudget,
    required this.currency,
    required this.purpose,
    required this.agentAddress,
    required this.signature,
    required this.expiresAt,
  });

  factory ShoppingPolicy.fromJson(Map<String, dynamic> json) {
    return ShoppingPolicy(
      policyId: json['policyId'] as String,
      maxBudget: (json['maxBudget'] as num).toDouble(),
      currency: json['currency'] as String,
      purpose: json['purpose'] as String,
      agentAddress: json['agentAddress'] as String,
      signature: json['signature'] as String,
      expiresAt: DateTime.parse(json['expiresAt'] as String),
    );
  }
}
