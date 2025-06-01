# Currency Service API Documentation

This document describes the REST API endpoints provided by the Currency Service. Each endpoint includes request and response examples.

---

## 1. Get Exchange Rate

- **Endpoint:** `/exchange-rate`
- **Method:** `POST`
- **Description:** Get the exchange rate between two currencies.

### Request Example

```json
{
  "sourceCurrency": "USD",
  "targetCurrency": "EUR"
}
```

### Response Example

```json
{
  "sourceCurrency": "USD",
  "targetCurrency": "EUR",
  "rate": 0.915
}
```

---

## 2. Currency Conversion

- **Endpoint:** `/currency-conversion`
- **Method:** `POST`
- **Description:** Convert an amount from one currency to another.

### Request Example

```json
{
  "sourceCurrency": "USD",
  "targetCurrency": "TRY",
  "amount": 100
}
```

### Response Example

```json
{
  "sourceCurrency": "USD",
  "targetCurrency": "TRY",
  "sourceAmount": 100,
  "targetAmount": 3200.45,
  "transactionId": 1717264000
}
```

---

## 3. Historical Currency Conversion

- **Endpoint:** `/historical-currency-conversion`
- **Method:** `POST`
- **Description:** Provides Historical Currency Conversion between currencies using a specified date in YYYY-MM-DD format.

### Request Example

```json
{
  "sourceCurrency": "USD",
  "targetCurrency": "EUR",
  "amount": 100,
  "date": "2024-05-15"
}
```

### Response Example

```json
{
  "sourceCurrency": "USD",
  "targetCurrency": "EUR",
  "sourceAmount": 100,
  "targetAmount": 92.2,
  "date": "2024-05-15"
}
```

---

## 4. Bulk Currency Conversion

- **Endpoint:** `/bulk-currency-conversion`
- **Method:** `POST` (multipart/form-data)
- **Description:** Convert multiple currency amounts in bulk by uploading a CSV file.

### Request Example

- Upload a file (parameter name: `file`) with content like:

```
amount;sourceCurrency;targetCurrency
100;USD;TRY
200;EUR;USD
```
- Pleae find the sample file under resources/templates/currency-conversion-file
### Response Example

```json
[
  {
    "sourceCurrency": "USD",
    "targetCurrency": "TRY",
    "sourceAmount": 100,
    "targetAmount": 3200.45,
    "transactionId": 1717264000
  },
  {
    "sourceCurrency": "EUR",
    "targetCurrency": "USD",
    "sourceAmount": 200,
    "targetAmount": 218.34,
    "transactionId": 1717264001
  }
]
```

---

## Notes

- All endpoints use `application/json` except for bulk conversion, which uses `multipart/form-data`.
- Dates are in `YYYY-MM-DD` format.
- While querying from api.currencylayer.com, 1-second delay was added for demo purposes due to trial mode limitations.

---

## Author

Developed by Ayse Ozdemir
