import 'dart:async';

class WalletService {
  String? _connectedAddress;
  String? _walletType; // 'metamask', 'walletconnect', 'phantom', etc.

  bool get isConnected => _connectedAddress != null;
  String? get address => _connectedAddress;
  String? get walletType => _walletType;

  // Simulated wallet connection for demo
  Future<bool> connectWallet() async {
    try {
      // Simulate wallet connection delay
      await Future.delayed(const Duration(seconds: 2));

      // Generate a mock wallet address (OWS compatible)
      _connectedAddress = '0x${_generateRandomHex(40)}';
      _walletType = 'OWS Wallet';

      return true;
    } catch (e) {
      _connectedAddress = null;
      _walletType = null;
      return false;
    }
  }

  Future<void> disconnectWallet() async {
    _connectedAddress = null;
    _walletType = null;
  }

  // Sign message simulation
  Future<String> signMessage(String message) async {
    if (!isConnected) {
      throw Exception('Wallet not connected');
    }

    await Future.delayed(const Duration(milliseconds: 500));

    // Generate mock signature
    return '0x${_generateRandomHex(128)}';
  }

  // Get formatted address (0x1234...5678)
  String getShortAddress() {
    if (_connectedAddress == null) return 'Not Connected';
    return '${_connectedAddress!.substring(0, 6)}...${_connectedAddress!.substring(_connectedAddress!.length - 4)}';
  }

  String _generateRandomHex(int length) {
    const chars = '0123456789abcdef';
    return List.generate(
      length,
      (index) => chars[(DateTime.now().microsecond + index) % chars.length],
    ).join();
  }
}

// Singleton instance
final walletService = WalletService();
