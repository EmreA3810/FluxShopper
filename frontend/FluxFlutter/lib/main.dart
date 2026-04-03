import 'package:flutter/material.dart';
import 'screens/home_screen.dart';

void main() {
  runApp(const FluxShopperApp());
}

class FluxShopperApp extends StatelessWidget {
  const FluxShopperApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'FluxShopper',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(
          seedColor: Colors.blue,
          brightness: Brightness.light,
        ),
        useMaterial3: true,
        fontFamily: 'Inter',
      ),
      home: const HomeScreen(),
    );
  }
}
