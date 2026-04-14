# Payment Orchestration System – Backend

## 1. Overview

This project is a simple backend system that handles payments and routes them to different providers based on the payment method.

The idea is similar to payment platforms like Yuno, where a single system decides:
- which provider to use  
- how to handle failures  
- how to avoid duplicate payments  

The focus is on clean backend design and real-world concepts.

---

## 2. Approach

The system is divided into layers so that each part has a clear responsibility:

- **Controller** → handles API requests  
- **Service** → manages payment flow  
- **Routing Engine** → decides provider  
- **Provider Layer** → simulates external systems  
- **Repository** → stores data  
- **Idempotency Check** → prevents duplicates  

This makes the system easy to extend and maintain.

---

## 3. Dependencies Used

Only required dependencies are used:

- **Spring Web** → for REST APIs  
- **Spring Data JPA** → for database operations  
- **H2 Database** → in-memory DB for development  
- **Resilience4j Retry** → for retry logic  
- **Spring AOP** → required for retry to work  
- **Lombok (optional)** → reduces boilerplate  
- **Spring Boot Test** → for testing  

---

## 4. Features

### 4.1 Create Payment API

- Checks idempotency key  
- Prevents duplicate processing  
- Creates payment record  
- Routes to provider  
- Updates status  

---

### 4.2 Fetch Payment API

- Fetch payment using ID  

---

### 4.3 Routing Logic

- **CARD → CardProvider**  
- **UPI → UPIProvider**  

Routing is separated for flexibility.

---

### 4.4 Retry & Failover

- Implemented using Resilience4j  
- Retries on failure  
- Calls fallback after retries  
- Marks payment as FAILED  

Note: Retry works only when exceptions are thrown.

---

### 4.5 Idempotency

- Uses `Idempotency-Key` header  
- Checks DB for existing request  
- Returns same response if already processed  

(Currently DB-based implementation)

---

### 4.6 Payment Status Tracking

Each payment goes through:

- INITIATED  
- PROCESSING  
- SUCCESS  
- FAILED  

---

## 5. Database Design

**Payments Table**

- id  
- userId  
- amount  
- currency  
- method  
- status  
- provider  
- idempotencyKey  
- createdAt  

---

## 6. Retry Flow

1. Call provider  
2. If failure → retry  
3. Retry again (based on config)  
4. If still fails → fallback method  
5. Mark payment as FAILED  

Important:
Retry works only when method is called through Spring proxy (not internal calls).

---

## 7. Design Decisions

- H2 used for simplicity  
- DB used for idempotency instead of Redis  
- Providers are simulated  

---

## 8. Summary

This project demonstrates:

- Payment orchestration flow  
- Routing based on payment type  
- Retry and fallback handling  
- Idempotency handling  
- Clean layered design  

---
