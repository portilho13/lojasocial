# Testing with Endpoints


## 1. Sign Up (Create a User)

*   **Method**: `POST`
*   **URL**: `http://localhost:3000/api/v1/auth/student/sign-up`
*   **Body** (JSON):
    ```json
    {
      "name": "John Doe",
      "studentNumber": "2023001",
      "course": "Computer Science",
      "academicYear": 2,
      "socialSecurityNumber": "123456789",
      "contact": "912345678",
      "email": "john.doe@example.com",
      "password": "Password123"
    }
    ```
*   **Expected Result**: `201 Created` with the user object.

## 2. Sign In (Get Tokens)

*   **Method**: `POST`
*   **URL**: `http://localhost:3000/api/v1/auth/student/sign-in`
*   **Body** (JSON):
    ```json
    {
      "email": "john.doe@example.com",
      "password": "Password123"
    }
    ```
*   **Expected Result**: `200 OK` with `accessToken` and `refreshToken`.
    ```json
    {
      "accessToken": "eyJhbG...",
      "refreshToken": "eyJhbG..."
    }
    ```

## 3. Logout

*   **Method**: `POST`
*   **URL**: `http://localhost:3000/api/v1/auth/logout`
*   **Headers**:
    *   `Authorization`: `Bearer <YOUR_ACCESS_TOKEN>`
    *   *(Copy the `accessToken` from the Sign In response and paste it here)*
*   **Expected Result**: `200 OK` with message `Logged out successfully`.

> [!TIP]
> In some cases, you can use **Variables** to automatically store the token.
> 1. In the "Sign In" request Tests tab, add:
>    *   **Action**: `Set Env Variable`
>    *   **Variable**: `token`
>    *   **Value**: `json.accessToken`
> 2. In the "Logout" request Auth tab:
>    *   **Type**: `Bearer`
>    *   **Token**: `{{token}}`

## 4. Refresh Token

*   **Method**: `POST`
*   **URL**: `http://localhost:3000/api/v1/auth/refresh`
*   **Headers**:
    *   `Authorization`: `Bearer <YOUR_REFRESH_TOKEN>`
    *   *(Copy the `refreshToken` from the Sign In response and paste it here)*
*   **Expected Result**: `200 OK` with new `accessToken` and `refreshToken`.

# User (Social Worker) Authentication

## 1. User Sign Up

*   **Method**: `POST`
*   **URL**: `http://localhost:3000/api/v1/auth/user/sign-up`
*   **Body (JSON)**:
    ```json
    {
      "name": "Social Worker 1",
      "userType": "admin",
      "contact": "123456789",
      "email": "worker@example.com",
      "password": "password123"
    }
    ```
*   **Expected Result**: `201 Created` with user details.

## 2. User Sign In

*   **Method**: `POST`
*   **URL**: `http://localhost:3000/api/v1/auth/user/sign-in`
*   **Body (JSON)**:
    ```json
    {
      "email": "worker@example.com",
      "password": "password123"
    }
    ```
*   **Expected Result**: `200 OK` with `accessToken` and `refreshToken`.

## 3. User Logout

*   **Method**: `POST`
*   **URL**: `http://localhost:3000/api/v1/auth/user/logout`
*   **Headers**:
    *   `Authorization`: `Bearer <YOUR_REFRESH_TOKEN>`
*   **Expected Result**: `200 OK`.

# Inventory Management

## 1. Create Product Type
*   **Method**: `POST`
*   **URL**: `http://localhost:3000/api/v1/inventory/types`
*   **Body (JSON)**:
    ```json
    {
      "description": "Food"
    }
    ```
*   **Expected Result**: `201 Created`.

## 2. List Product Types
*   **Method**: `GET`
*   **URL**: `http://localhost:3000/api/v1/inventory/types`
*   **Expected Result**: `200 OK`.

## 3. Create Product
*   **Method**: `POST`
*   **URL**: `http://localhost:3000/api/v1/inventory/products`
*   **Body (JSON)**:
    ```json
    {
      "name": "Rice 1kg",
      "typeId": 1
    }
    ```
    *Replace `1` with the `id` from Step 1.*
*   **Expected Result**: `201 Created`. **Note the `id` returned.**

## 4. List Products
*   **Method**: `GET`
*   **URL**: `http://localhost:3000/api/v1/inventory/products`
*   **Expected Result**: `200 OK`.

## 5. Create Stock (Add Inventory)
*   **Method**: `POST`
*   **URL**: `http://localhost:3000/api/v1/inventory/stocks`
*   **Body (JSON)**:
    ```json
    {
      "productId": 1,
      "quantity": 50,
      "expiryDate": "2026-12-31T00:00:00.000Z",
      "location": "Shelf A"
    }
    ```
    *Replace `1` with the `id` from Step 3.*
*   **Expected Result**: `201 Created`.

## 6. List Stock
*   **Method**: `GET`
*   **URL**: `http://localhost:3000/api/v1/inventory/stocks`
*   **Expected Result**: `200 OK`.

## 7. Update Stock
*   **Method**: `PATCH`
*   **URL**: `http://localhost:3000/api/v1/inventory/stocks/1`
    *Replace `1` with the stock ID.*
*   **Body (JSON)**:
    ```json
    {
      "quantity": 45
    }
    ```
*   **Expected Result**: `200 OK`.

## 8. Get Stock Summary
*   **Method**: `GET`
*   **URL**: `http://localhost:3000/api/v1/inventory/stocks/summary`
*   **Expected Result**: `200 OK`.

## 9. Get Expiring Stock
*   **Method**: `GET`
*   **URL**: `http://localhost:3000/api/v1/inventory/stocks/expiring?days=60`
*   **Expected Result**: `200 OK`.

## 10. Delete Stock
*   **Method**: `DELETE`
*   **URL**: `http://localhost:3000/api/v1/inventory/stocks/1`
    *Replace `1` with the stock ID.*
*   **Expected Result**: `204 No Content`.

## 4. User Refresh Token

*   **Method**: `POST`
*   **URL**: `http://localhost:3000/api/v1/auth/user/refresh`
*   **Headers**:
    *   `Authorization`: `Bearer <YOUR_REFRESH_TOKEN>`
*   **Expected Result**: `200 OK` with new tokens.


