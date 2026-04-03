class Product {
  final int id;
  final String name;
  final String category;
  final double price;
  final String currency;
  final List<String> tags;

  Product({
    required this.id,
    required this.name,
    required this.category,
    required this.price,
    this.currency = 'USDC',
    required this.tags,
  });

  factory Product.fromJson(Map<String, dynamic> json) {
    return Product(
      id: json['id'] as int,
      name: json['name'] as String,
      category: json['category'] as String,
      price: (json['price'] as num).toDouble(),
      currency: json['currency'] as String? ?? 'USDC',
      tags: (json['tags'] as List<dynamic>).map((e) => e as String).toList(),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'category': category,
      'price': price,
      'currency': currency,
      'tags': tags,
    };
  }
}
