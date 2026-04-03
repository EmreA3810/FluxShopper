# FluxShopper - Test Rehberi

## 🚀 Hızlı Başlangıç

### 1. Backend'i Başlat (Java Spring Boot)

```bash
cd C:\Users\emrea\Desktop\FluxShopper

# Option 1: Maven ile (önerilir)
.\mvnw spring-boot:run

# Option 2: JAR ile
mvn clean package
java -jar target/fluxshopper-0.0.1-SNAPSHOT.jar
```

Backend **http://localhost:8080** adresinde çalışacak.

### 2. Flutter Paketlerini Yükle

```bash
cd C:\Users\emrea\Desktop\flutter_apps\abc
flutter pub get
```

### 3. Flutter Uygulamasını Çalıştır

**Chrome üzerinde (önerilir - hızlı):**
```bash
flutter run -d chrome
```

**Windows Desktop:**
```bash
flutter run -d windows
```

**Android Emulator (Android lisansları gerekir):**
```bash
flutter doctor --android-licenses   # İlk seferlik
flutter run -d <device-id>
```

---

## 🧪 Test Senaryoları

### Senaryo 1: Konferans Kombini (150 USDC)
1. Budget: 150 USDC
2. Style: 👔 Conference
3. "Deploy Agent" butonuna bas
4. Agent animasyonlarını izle (8-10 saniye)
5. Receipt ekranında kombini gör

**Beklenen Sonuç:**
- 3-4 ürün (polo + chino + ayakkabı + aksesuar)
- Toplam < 150 USDC
- x402 payment transaction hash'leri
- OWS Policy bilgileri

### Senaryo 2: Casual Kombini (100 USDC)
1. Budget: 100 USDC
2. Style: 👕 Casual
3. Deploy Agent
4. Receipt kontrolü

### Senaryo 3: Hata Durumu
**Backend kapalıyken:**
1. Flutter uygulamasını çalıştır
2. Deploy Agent'a bas
3. Hata ekranını gör
4. "Try Again" ile home'a dön

---

## 🔍 Backend API Kontrolü

### Health Check
```bash
curl http://localhost:8080/api/shopping/health
```

Beklenen: `{"status":"ok","service":"fluxshopper-backend"}`

### Manuel Checkout Test
```bash
curl -X POST http://localhost:8080/api/shopping/checkout \
  -H "Content-Type: application/json" \
  -d '{"budget":150,"purpose":"conference","currency":"USDC"}'
```

Beklenen: Receipt JSON (selectedProducts, payments, policy)

---

## 📱 Platform Seçimi

| Platform | Hız | Önerilen | Not |
|----------|-----|----------|-----|
| Chrome | ⚡⚡⚡ | ✅ | En hızlı development |
| Windows | ⚡⚡ | ✅ | Native desktop |
| Android | ⚡ | ⚠️ | Lisans gerekir |

---

## 🎨 Ekran Görüntüleri İçin

1. **Home Screen:** Bütçe slider & stil seçici
2. **Agent Screen:** Animasyon (loading) ekranı
3. **Receipt Screen:** Kombin kartları + payment detayları

Chrome DevTools ile (F12) → **Device Toolbar** açıp iPhone 14 Pro boyutunda screenshot al.

---

## ⚠️ Bilinen Sorunlar

1. **Flutter PATH sorunu:** Terminal'den `flutter` komutu çalışmazsa, VS Code terminal'inden çalıştır.
2. **CORS hatası:** Backend'de CORS enabled, sorun olmamalı.
3. **Android lisansları:** İlk seferlik `flutter doctor --android-licenses` çalıştır.

---

## 🎯 Demo Hazırlığı

### Video İçin Akış:
1. **[0:00-0:10]** Home ekranı → Budget 200, Conference seçili
2. **[0:10-0:20]** Agent animasyonları
3. **[0:20-0:40]** Receipt ekranı → Ürünler, payments, policy
4. **[0:40-0:50]** "New Shopping" → Casual 100 USDC
5. **[0:50-1:00]** İkinci alışveriş fişi

### Slayt Noktaları:
- **OWS Policy Engine** kullanımı
- **x402 payment** entegrasyonu
- **AI keyword matching** (şimdilik basit, ileride GPT)
- **Budget constraints** (agent bütçeyi aşamıyor)

---

## 🚀 Sonraki Adımlar (Post-Hackathon)

- [ ] OpenAI GPT-4 entegrasyonu (akıllı ürün önerisi)
- [ ] Gerçek x402 SDK entegrasyonu
- [ ] Ürün görselleri (API'den)
- [ ] Kullanıcı hesapları & alışveriş geçmişi
- [ ] Gerçek tedarikçi API'leri

---

**Hazırladı:** GitHub Copilot
**Tarih:** 2026-04-03
**Süre:** 10 saat hackathon 🚀
