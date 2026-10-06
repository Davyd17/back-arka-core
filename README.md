# arka-core

**Business domain service** for Arka — a B2B technology distribution platform. Owns orders, products, inventory, suppliers, shipping, and reporting.

> This service is one half of the Arka system. For the full architecture (service boundaries, event-driven flow, infra, diagrams), see the [system-level documentation](#) and the sibling repo, [`arka-auth`](#).

---

## What this service owns

- **Orders & shopping cart** — order lifecycle with enforced status transitions, cart abandonment detection
- **Products & inventory** — catalog with pagination (`PageWrapper<T>`), warehouse inventory tracking, low-stock detection
- **Suppliers & shipping** — supplier/company management, shipping detail tracking
- **Reporting** — weekly sales and low-stock reports, generated asynchronously via SQS and a factory/strategy pattern for output format (CSV today, PDF planned)
- **Order-status notifications** — triggers email delivery (handled via `arka-auth`'s notification path) on status change, decoupled through SQS

This service does **not** own authentication or user identity — it trusts JWTs issued by `arka-auth` and validates them statelessly, with no call back to the auth service to check a token.

---

## Architecture

Follows the same Clean/Hexagonal layering as `arka-auth`:

```
domain/model        → entities, enums, exceptions — no Spring dependency
domain/usecase       → use cases, DTOs (In/Out, Command/Event), mappers, gateways
infrastructure/
  driven-adapters/   → jpa-repository, aws-provider, security-provider
  entry-points/      → api-rest, security, sqs-listener
main-app/boot        → Spring Boot entry point, wiring, configuration
```

Internal-only endpoints (report triggers, inter-service contact creation) are reachable only over private IPs within the VPC, with security groups as the network-level firewall — not just an application check.

---

## Getting Started

### Prerequisites
- Java 21
- Docker
- PostgreSQL (or use the provided Docker setup)

### Run locally

```bash
git clone https://github.com/Davyd17/back-arka-core.git
cd arka-core

./gradlew clean build -x test
docker build -f deployment/Dockerfile -t arka-core .
docker run -p 8080:8080 --env-file .env arka-core
```

### Environment variables

| Variable | Description |
|---|---|
| `SPRING_PROFILES_ACTIVE` | Active Spring profile (`dev`, `prod`, etc.) |
| `API_BASE_URL` | Public base URL this service is served from |
| `SERVER_PORT` | Public HTTP port the app listens on |
| `INTERNAL_PORT` | Separate port used for internal-only endpoints, enforced by `InternalPortFilter` |
| `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USERNAME`, `DB_PASSWORD` | PostgreSQL connection |
| `JWT_SECRET` | Signing key used to validate JWTs issued by `arka-auth` |
| `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`, `AWS_REGION` | AWS credentials — S3, SES, SQS access |
| `BUCKET_NAME` | S3 bucket holding HTML email templates |
| `S3_KEY_HTML_TEMPLATE_ORDER_STATE_EMAIL` | S3 key for the order-status-change email template |
| `SES_SENDER_EMAIL` | Verified SES sender address for outgoing emails |
| `ORDER_STATUS_NOTIFY_QUEUE` | SQS queue name/URL for order-status notification events |
| `SALES_REPORT_QUEUE` | SQS queue name/URL for weekly sales report events |
| `LOW_STOCK_REPORT_QUEUE` | SQS queue name/URL for low-stock report events |
| `AUTH_SERVICE_BASE_URL` | Base URL used to call `arka-auth`, if applicable |

---

## Testing

```bash
./gradlew test
```

JUnit 5, Mockito, AssertJ for domain/use case tests; Testcontainers (Postgres) for JPA adapter tests; `@WebMvcTest` + MockMvc for controllers.

---

## API Documentation

- **Swagger UI:** `/swagger-ui.html`
- **Postman collection:** [link](#)
- **Full system docs:** [landing page](#)

---

## Contact

**David Correa** — Java Backend Developer, Medellín, Colombia
📧 davidcq55@gmail.com
