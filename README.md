# ms-pedidos

Puedes tener este proyecto en **su propio repositorio GitHub** (solo esta carpeta) o junto a `ms-productos` en una carpeta padre; el `pom.xml` ya no depende de ningún otro módulo.

Microservicio REST para registrar y gestionar pedidos (Spring Boot, PostgreSQL/Neon, Render). El **total** se calcula en el servidor: `cantidad * precioUnitario`.

## Tecnologías

- Java 21
- Spring Boot 3.4 (Web, Data JPA, Validation)
- PostgreSQL (Neon)
- Lombok
- Docker

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/pedidos` | Crear pedido (estado inicial `REGISTRADO`, total calculado) |
| GET | `/api/pedidos` | Listar todos |
| GET | `/api/pedidos/{id}` | Buscar por ID |
| PATCH | `/api/pedidos/{id}/estado` | Actualizar solo el estado |
| DELETE | `/api/pedidos/{id}` | Cancelación lógica (`estado = CANCELADO`) |

Estados permitidos en PATCH: `REGISTRADO`, `PAGADO`, `ENVIADO`, `CANCELADO`.

## Variables de entorno

| Variable | Descripción |
|----------|-------------|
| `DB_URL` | JDBC URL de PostgreSQL (Neon) |
| `DB_USERNAME` | Usuario |
| `DB_PASSWORD` | Contraseña |
| `PORT` | Puerto (Render lo define; local por defecto `8080`) |

Puedes usar la **misma instancia Neon** que `ms-productos` con otra base de datos, otra **schema**, o tablas distintas (`pedidos`); lo importante es que este servicio tenga **su propia configuración** de conexión.

## Ejecución local

```bash
export DB_URL="jdbc:postgresql://HOST:5432/DBNAME?sslmode=require"
export DB_USERNAME="tu_usuario"
export DB_PASSWORD="tu_password"
cd ms-pedidos
mvn spring-boot:run
```

Ejemplo de creación:

```bash
curl -X POST http://localhost:8081/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{"cliente":"Juan Pérez","correoCliente":"juan@email.com","productoId":1,"nombreProducto":"Laptop Lenovo","cantidad":2,"precioUnitario":3500.00}'
```

*(Si ambos servicios corren en la misma máquina, cambia el puerto de uno con `PORT=8081` al arrancar el segundo.)*

## Despliegue en Render

Igual que en productos: Web Service, Dockerfile en raíz, variables `DB_*` y comprobar despertar del plan gratuito.

## URL del servicio desplegado

`https://TU-SERVICIO-PEDIDOS.onrender.com/api/pedidos`
