import 'package:flutter/material.dart';
import '../services/shopping_service.dart';
import 'receipt_screen.dart';

class AgentScreen extends StatefulWidget {
  final double budget;
  final String purpose;

  const AgentScreen({super.key, required this.budget, required this.purpose});

  @override
  State<AgentScreen> createState() => _AgentScreenState();
}

class _AgentScreenState extends State<AgentScreen>
    with SingleTickerProviderStateMixin {
  late AnimationController _controller;
  int _currentStep = 0;
  bool _hasError = false;
  String _errorMessage = '';

  final List<Map<String, dynamic>> _steps = [
    {
      'icon': Icons.verified_user,
      'title': 'Signing Policy',
      'subtitle': 'Agent authorizing with OWS Policy Engine...',
      'duration': 2000,
    },
    {
      'icon': Icons.search,
      'title': 'Scanning Products',
      'subtitle': 'AI analyzing catalog for best matches...',
      'duration': 2500,
    },
    {
      'icon': Icons.shopping_cart,
      'title': 'Selecting Items',
      'subtitle': 'Building your perfect outfit...',
      'duration': 2000,
    },
    {
      'icon': Icons.payment,
      'title': 'Processing Payment',
      'subtitle': 'x402 payment to suppliers...',
      'duration': 2500,
    },
  ];

  @override
  void initState() {
    super.initState();
    _controller = AnimationController(
      vsync: this,
      duration: const Duration(milliseconds: 1500),
    )..repeat();
    _startAgentWorkflow();
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  Future<void> _startAgentWorkflow() async {
    try {
      // Step through animation phases
      for (int i = 0; i < _steps.length; i++) {
        setState(() {
          _currentStep = i;
        });
        await Future.delayed(
          Duration(milliseconds: _steps[i]['duration'] as int),
        );
      }

      // Make actual API call
      final service = ShoppingService();
      final receipt = await service.checkout(
        budget: widget.budget,
        purpose: widget.purpose,
      );

      // Navigate to receipt
      if (mounted) {
        Navigator.pushReplacement(
          context,
          MaterialPageRoute(
            builder: (context) => ReceiptScreen(receipt: receipt),
          ),
        );
      }
    } catch (e) {
      setState(() {
        _hasError = true;
        _errorMessage = _formatErrorMessage(e);
      });
    }
  }

  String _formatErrorMessage(Object error) {
    final raw = error.toString();
    final lower = raw.toLowerCase();

    if (lower.contains('endpoint not found') ||
        lower.contains('no static resource')) {
      return 'Backend endpoint bulunamadi. Backend servisini yeniden baslatip tekrar deneyin.';
    }

    if (lower.contains('failed to checkout') ||
        lower.contains('statusCode: 404')) {
      return 'Checkout servisine ulasilamadi (404). Backend API route ayarlarini kontrol edin.';
    }

    if (lower.contains('socketexception') ||
        lower.contains('connection refused') ||
        lower.contains('network error')) {
      return 'Backend baglantisi kurulamadı. http://localhost:8080 adresinin acik oldugundan emin olun.';
    }

    // Keep error text concise for UI.
    if (raw.length > 220) {
      return '${raw.substring(0, 220)}...';
    }

    return raw;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        decoration: BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.topLeft,
            end: Alignment.bottomRight,
            colors: [Colors.purple.shade900, Colors.blue.shade900],
          ),
        ),
        child: SafeArea(
          child: _hasError ? _buildErrorView() : _buildWorkflowView(),
        ),
      ),
    );
  }

  Widget _buildWorkflowView() {
    return Padding(
      padding: const EdgeInsets.all(24.0),
      child: Column(
        children: [
          const SizedBox(height: 40),

          // Agent Avatar with Animation
          Container(
            width: 120,
            height: 120,
            decoration: BoxDecoration(
              shape: BoxShape.circle,
              color: Colors.white.withOpacity(0.1),
              border: Border.all(
                color: Colors.white.withOpacity(0.3),
                width: 4,
              ),
            ),
            child: Center(
              child: RotationTransition(
                turns: _controller,
                child: const Icon(
                  Icons.smart_toy,
                  size: 60,
                  color: Colors.white,
                ),
              ),
            ),
          ),

          const SizedBox(height: 24),

          const Text(
            'FluxShopper Agent',
            style: TextStyle(
              fontSize: 32,
              fontWeight: FontWeight.bold,
              color: Colors.white,
            ),
          ),

          const SizedBox(height: 8),

          Text(
            'Budget: ${widget.budget.toStringAsFixed(0)} USDC',
            style: const TextStyle(fontSize: 18, color: Colors.white70),
          ),

          const SizedBox(height: 60),

          // Progress Steps
          Expanded(
            child: ListView.builder(
              itemCount: _steps.length,
              itemBuilder: (context, index) {
                final step = _steps[index];
                final isActive = index == _currentStep;
                final isCompleted = index < _currentStep;

                return Padding(
                  padding: const EdgeInsets.only(bottom: 32.0),
                  child: Row(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      // Step Indicator
                      Column(
                        children: [
                          AnimatedContainer(
                            duration: const Duration(milliseconds: 300),
                            width: 56,
                            height: 56,
                            decoration: BoxDecoration(
                              shape: BoxShape.circle,
                              color: isActive
                                  ? Colors.white
                                  : isCompleted
                                  ? Colors.green
                                  : Colors.white.withOpacity(0.2),
                              boxShadow: isActive
                                  ? [
                                      BoxShadow(
                                        color: Colors.white.withOpacity(0.5),
                                        blurRadius: 20,
                                        spreadRadius: 5,
                                      ),
                                    ]
                                  : null,
                            ),
                            child: Icon(
                              isCompleted
                                  ? Icons.check
                                  : step['icon'] as IconData,
                              color: isActive || isCompleted
                                  ? Colors.blue.shade900
                                  : Colors.white.withOpacity(0.5),
                              size: 28,
                            ),
                          ),
                          if (index < _steps.length - 1)
                            Container(
                              width: 2,
                              height: 40,
                              color: isCompleted
                                  ? Colors.green
                                  : Colors.white.withOpacity(0.2),
                            ),
                        ],
                      ),

                      const SizedBox(width: 20),

                      // Step Content
                      Expanded(
                        child: Column(
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: [
                            const SizedBox(height: 8),
                            Text(
                              step['title'] as String,
                              style: TextStyle(
                                fontSize: 20,
                                fontWeight: FontWeight.bold,
                                color: isActive || isCompleted
                                    ? Colors.white
                                    : Colors.white.withOpacity(0.5),
                              ),
                            ),
                            const SizedBox(height: 4),
                            Text(
                              step['subtitle'] as String,
                              style: TextStyle(
                                fontSize: 14,
                                color: isActive || isCompleted
                                    ? Colors.white70
                                    : Colors.white.withOpacity(0.3),
                              ),
                            ),
                            if (isActive)
                              Padding(
                                padding: const EdgeInsets.only(top: 12.0),
                                child: LinearProgressIndicator(
                                  backgroundColor: Colors.white.withOpacity(
                                    0.2,
                                  ),
                                  valueColor:
                                      const AlwaysStoppedAnimation<Color>(
                                        Colors.white,
                                      ),
                                ),
                              ),
                          ],
                        ),
                      ),
                    ],
                  ),
                );
              },
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildErrorView() {
    return Center(
      child: Padding(
        padding: const EdgeInsets.all(24.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Icon(Icons.error_outline, size: 80, color: Colors.redAccent),
            const SizedBox(height: 24),
            const Text(
              'Oops! Something went wrong',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
                color: Colors.white,
              ),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 16),
            Text(
              _errorMessage,
              style: const TextStyle(fontSize: 14, color: Colors.white70),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 32),
            ElevatedButton(
              onPressed: () {
                Navigator.pop(context);
              },
              style: ElevatedButton.styleFrom(
                backgroundColor: Colors.white,
                foregroundColor: Colors.blue.shade900,
                padding: const EdgeInsets.symmetric(
                  horizontal: 32,
                  vertical: 16,
                ),
              ),
              child: const Text('Try Again'),
            ),
          ],
        ),
      ),
    );
  }
}
