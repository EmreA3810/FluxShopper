# FluxShopper Backend (Spring Boot MVP)

This repository now contains the FluxShopper backend MVP built with Spring Boot.

## Tech Stack

- Java 17+
- Spring Boot 3
- Maven

## Implemented Features

- Product catalog from JSON file (15 products)
- Product listing endpoint
- Keyword/style matcher with budget control
- Mock OWS policy creation and signature
- Mock x402 payment per selected product
- Mock supplier payment endpoint
- Receipt generation for frontend consumption

## Project Structure

- `pom.xml`
- `src/main/java/com/fluxshopper`
- `src/main/resources/data/products.json`

## API Endpoints

1. `GET /api/shopping/health`
2. `GET /api/products`
3. `POST /api/shopping/checkout`
4. `POST /api/supplier/pay/{category}`

### Checkout Request Example

```json
{
	"budget": 150,
	"purpose": "konferans kombini",
	"currency": "USDC"
}
```

### Checkout Response (Shape)

```json
{
	"receiptId": "RCPT-...",
	"policy": {
		"policyId": "POL-...",
		"maxBudget": 150,
		"currency": "USDC",
		"purpose": "konferans kombini",
		"agentAddress": "0xFluxAgent001",
		"expiresAt": "...",
		"signature": "SIG-..."
	},
	"selectedProducts": [],
	"payments": [],
	"totalSpent": 0,
	"remainingBudget": 150,
	"createdAt": "..."
}
```

## Run

```bash
mvn spring-boot:run
```

If Maven is not installed globally, you can use the local Maven binary:

```bash
.\\.tools\\apache-maven-3.9.9\\bin\\mvn.cmd spring-boot:run
```

## Verify

```bash
curl http://localhost:8080/api/shopping/health
curl http://localhost:8080/api/products
```
