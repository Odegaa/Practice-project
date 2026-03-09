# 🚀 Practice Project: ERP System (HR, CRM & Ads Analytics)

A comprehensive Information Management System designed to streamline employee workflows, customer relations (CRM), and advertising campaigns with advanced analytics for executive management.

---

## 🛠 Tech Stack
* **Java 17** & **Spring Boot 3**
* **Spring Security** (JWT Authentication & Role-Based Access Control)
* **Spring Data JPA** (PostgreSQL)
* **Lombok** & **MapStruct**
* **Swagger UI** (OpenAPI 3.0)
* **Docker** & **Docker Compose**
* **Logback** (Action auditing and error logging to local files)

---

## 🔑 Role-Based Access Control (RBAC)
The system features a strict permission model based on user roles:

| Role | Responsibilities |
| :--- | :--- |
| **ADMIN** | Full system access, role management, and data deletion. |
| **DIRECTOR** | Access to analytical dashboards and financial performance reports. |
| **HR_MANAGER** | Personnel management, directory management (Regions/Cities), and passport data. |
| **CUSTOMER_MANAGER** | CRM operations, managing individual and legal entity clients. |
| **SALES_MANAGER** | Management of advertising campaigns and sales workflows. |
| **EMPLOYEE** | Basic read-only access to general information. |


---

## 📊 Core Modules
1. **HR Module**: Manage employees, regional structures, and residential addresses.
2. **CRM Module**: Full lifecycle management for diverse client types (Individuals & Legal Entities).
3. **Ads Analytics**: Specialized endpoints for tracking advertising efficiency over the last 30 days.
4. **Security**: Robust authentication powered by JWT.
5. **Audit System**: All user actions and system exceptions are logged into the `/logs` directory for compliance.

---

## 📩 Contact & Support
If you have any questions or want to connect, feel free to reach out:

* **Developer:** odegaa
* **Email:** odegaa0202@gmail.com
* **Telegram:** @odegaa (https://t.me/odegaa)
* **LinkedIn:** Ruslan Kazakbaev (https://linkedin.com/in/odegaa)
* **Docker Hub:** [https://hub.docker.com/u/odegaa](https://hub.docker.com/repositories/odegaa)
* **GitHub:** [https://github.com/Odegaa]

---

## 🚀 Quick Start (via Docker)

Ensure you have Docker installed, then run the following commands:

```bash
# Pull the latest image
docker pull odegaa/practice-project

# Run the container with log persistence
docker run -p 8083:8083 -v C:/erp_logs:/app/logs odegaa/practice-project

📖 API Documentation & Swagger UI
The project comes with built-in interactive documentation. This allows you to test all API endpoints directly from your browser.

How to use Swagger:
- Access the UI: Once the application is running, open:
  http://localhost:8083/swagger-ui/index.html

Authentication:
 - Use the auth-controller to log in and receive a JWT Token.
 - Click the "Authorize" button at the top of the Swagger page.
 - Enter your token in the format: Bearer <your_token_here>.

Explore: You can now expand any controller and click "Try it out" to send real requests to the server.

⚙️ Environment Configuration
The system can be configured using the following environment variables:
 - SPRING_DATASOURCE_URL: PostgreSQL connection string.
 - SPRING_DATASOURCE_USERNAME: Database username.
 - SPRING_DATASOURCE_PASSWORD: Database password.
 - JWT_SECRET_KEY: Custom secret key for token signing.
