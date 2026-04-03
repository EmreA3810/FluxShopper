# 🛍️ FluxShopper - AI Personal Shopper

OWS Policy Engine ve x402 ile güçlendirilmiş akıllı alışveriş asistanı.

## 🚀 Hızlı Başlangıç

### 1️⃣ Backend'i Başlat

Yeni bir terminal aç:
```bash
cd C:\Users\emrea\Desktop\FluxShopper
mvn spring-boot:run
```

Backend **http://localhost:8080** adresinde çalışacak.

### 2️⃣ Flutter Uygulamasını Çalıştır

```bash
cd C:\Users\emrea\Desktop\flutter_apps\abc

# Paketleri yükle (ilk seferlik)
flutter pub get

# Chrome'da çalıştır (önerilir)
flutter run -d chrome

# Veya Windows Desktop
flutter run -d windows
```

## 📱 Nasıl Kullanılır?

1. **Bütçe belirle:** Slider ile 50-500 USDC arası seç
2. **Stil seç:** Conference / Casual / Formal / Sport
3. **"Deploy Agent"** butonuna bas
4. Agent animasyonlarını izle (8-10 saniye)
5. **Alışveriş fişini** gör:
   - Seçilen ürünler
   - x402 payment detayları
   - OWS Policy bilgileri

## 🏗️ Mimari

```
Flutter App (Frontend)
    ↓ HTTP/REST
Java Spring Boot (Backend)
    ↓
OWS Policy Engine + x402 Payments
```

## 📂 Proje Yapısı

```
abc/                          # Flutter Frontend
├── lib/
│   ├── main.dart            # Entry point
│   ├── models/              # Data models
│   ├── screens/             # UI screens
│   └── services/            # API services
└── pubspec.yaml

FluxShopper/                 # Java Backend
├── src/main/java/
│   └── com/fluxshopper/
│       ├── controller/      # REST endpoints
│       ├── service/         # Business logic
│       └── model/           # Data models
└── pom.xml
```

## 🎯 Özellikler

- ✅ Bütçe odaklı alışveriş
- ✅ OWS Policy Engine entegrasyonu
- ✅ x402 ödeme simülasyonu
- ✅ AI keyword matching
- ✅ Animasyonlu agent workflow
- ✅ Detaylı alışveriş fişi

## 🧪 Test

Backend health check:
```bash
curl http://localhost:8080/api/shopping/health
```

Detaylı test senaryoları için: [TEST_GUIDE.md](TEST_GUIDE.md)

## 🛠️ Teknolojiler

**Frontend:**
- Flutter 3.35.2
- HTTP client
- Material Design 3

**Backend:**
- Java 17+
- Spring Boot
- Maven
- OWS Policy Engine (simulated)
- x402 Payments (simulated)

## 📸 Ekran Görüntüleri

1. **Home Screen:** Bütçe & stil girişi
2. **Agent Screen:** AI workflow animasyonları
3. **Receipt Screen:** Kombin + payment detayları

## 🏆 Hackathon

Proje 10 saatlik hackathon için tasarlandı:
- ⏱️ Başlangıç: 20:30
- 🎯 Teslim: 07:00
- 🎨 Demo video: 2 dakika

## 📝 Notlar

- Mock ürün kataloğu: 15 ürün
- Backend port: 8080
- CORS enabled (localhost:*)
- Policy signature: simulated
- x402 payments: simulated

## 🚀 Sonraki Adımlar

- [ ] OpenAI GPT-4 entegrasyonu
- [ ] Gerçek x402 SDK
- [ ] Ürün görselleri
- [ ] Kullanıcı hesapları
- [ ] Gerçek supplier API'leri

---

**Made with ❤️ for AI Agent Hackathon**
