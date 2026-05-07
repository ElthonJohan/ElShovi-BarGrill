# 📋 GUÍA COMPLETA: Sistema RBAC (Role-Based Access Control)

Tu aplicación ahora es **totalmente configurable** con un sistema de permisos basado en roles. El administrador puede crear usuarios, asignar roles y controlar qué módulos puede acceder cada rol.

---

## 📊 Arquitectura del Sistema

```
Usuario
  ↓ (tiene múltiples)
Roles
  ↓ (tiene acceso a múltiples)
Módulos
  ↓ (representa)
Funcionalidades del Sistema
```

### Componentes Creados:

1. **Modelo `Module`** - Representa módulos/funcionalidades del sistema
2. **Relación Role ↔ Module** - Cada rol puede tener múltiples módulos
3. **DTOs mejorados** - Para gestionar la configuración
4. **AdminController** - Panel de control completo para administradores

---

## 🚀 Inicialización Automática

Cuando la aplicación inicia, **automáticamente**:

1. ✅ Crea 10 módulos principales:
   - USUARIOS, ROLES, MODULOS, PEDIDOS, MESAS, MENU, RESERVACIONES, PAGOS, ENTREGAS, REPORTES

2. ✅ Crea el rol ADMIN con acceso a todos los módulos

3. ✅ Crea el usuario administrador por defecto:
   - **Email**: `admin@elshovi.com`
   - **Usuario**: `admin`
   - **Contraseña**: `admin123` ⚠️ **CAMBIAR EN PRODUCCIÓN**

---

## 🔐 Endpoints del Panel de Administración

Todos los endpoints requieren el rol `ADMIN`. Base URL: `http://localhost:8080/admin`

### GESTIÓN DE USUARIOS

#### 1. Obtener todos los usuarios
```http
GET /admin/users
```

#### 2. Obtener usuario por ID
```http
GET /admin/users/{id}
```

#### 3. Crear nuevo usuario con roles
```http
POST /admin/users
Content-Type: application/json

{
  "email": "nuevo@elshovi.com",
  "userName": "nuevo_usuario",
  "password": "Segura1234",
  "fullName": "Nombre Completo",
  "roleIds": [1, 2]  // IDs de los roles a asignar
}
```

#### 4. Editar usuario
```http
PUT /admin/users/{id}
Content-Type: application/json

{
  "email": "actualizado@elshovi.com",
  "userName": "usuario_editado",
  "password": "NuevaContraseña123",
  "fullName": "Nombre Actualizado",
  "active": true,
  "roleIds": [1]
}
```

#### 5. Asignar roles a un usuario
```http
POST /admin/users/{id}/assign-roles
Content-Type: application/json

{
  "userId": 5,
  "roleIds": [1, 2, 3]  // Reemplaza los roles del usuario
}
```

#### 6. Eliminar usuario
```http
DELETE /admin/users/{id}
```

#### 7. Listar usuarios con paginación
```http
GET /admin/users/paginated?page=0&size=10
```

---

### GESTIÓN DE ROLES

#### 1. Obtener todos los roles
```http
GET /admin/roles
```

#### 2. Obtener rol por ID
```http
GET /admin/roles/{id}
```

#### 3. Crear nuevo rol
```http
POST /admin/roles
Content-Type: application/json

{
  "name": "MESERO",
  "description": "Rol para meseros del restaurante"
}
```

#### 4. Editar rol
```http
PUT /admin/roles/{id}
Content-Type: application/json

{
  "name": "MESERO",
  "description": "Mesero con permisos actualizados"
}
```

#### 5. Asignar módulos a un rol
```http
POST /admin/roles/{id}/assign-modules
Content-Type: application/json

{
  "roleId": 2,
  "moduleIds": [4, 5, 6]  // IDs de módulos (PEDIDOS, MESAS, MENU)
}
```

#### 6. Eliminar rol
```http
DELETE /admin/roles/{id}
```

---

### GESTIÓN DE MÓDULOS

#### 1. Obtener todos los módulos
```http
GET /admin/modules
```

**Respuesta ejemplo:**
```json
[
  {
    "idModule": 1,
    "name": "USUARIOS",
    "description": "Gestión de usuarios del sistema",
    "path": "/admin/users",
    "active": true
  },
  {
    "idModule": 4,
    "name": "PEDIDOS",
    "description": "Gestión de órdenes/pedidos",
    "path": "/orders",
    "active": true
  }
]
```

#### 2. Obtener módulo por ID
```http
GET /admin/modules/{id}
```

#### 3. Crear nuevo módulo
```http
POST /admin/modules
Content-Type: application/json

{
  "name": "INVENTARIO",
  "description": "Gestión del inventario",
  "path": "/inventory",
  "active": true
}
```

#### 4. Editar módulo
```http
PUT /admin/modules/{id}
Content-Type: application/json

{
  "name": "INVENTARIO",
  "description": "Gestión del inventario actualizada",
  "path": "/inventory",
  "active": true
}
```

#### 5. Eliminar módulo
```http
DELETE /admin/modules/{id}
```

---

## 📝 Ejemplos de Uso Completo

### Ejemplo 1: Crear usuario "Mesero"

1. **Primero, crear el rol "MESERO"** (si no existe):
```bash
POST http://localhost:8080/admin/roles
{
  "name": "MESERO",
  "description": "Acceso a funciones de mesero"
}
# Respuesta: idRole = 2
```

2. **Asignar módulos al rol MESERO**:
```bash
POST http://localhost:8080/admin/roles/2/assign-modules
{
  "roleId": 2,
  "moduleIds": [4, 5, 6]  # Módulos: PEDIDOS, MESAS, MENU
}
```

3. **Crear usuario mesero**:
```bash
POST http://localhost:8080/admin/users
{
  "email": "carlos@elshovi.com",
  "userName": "carlos_mesero",
  "password": "Mesero1234",
  "fullName": "Carlos García",
  "roleIds": [2]  # Rol MESERO
}
```

---

### Ejemplo 2: Crear usuario "Chef"

1. **Crear rol CHEF**:
```bash
POST http://localhost:8080/admin/roles
{
  "name": "CHEF",
  "description": "Acceso a gestión de menú y cocina"
}
# Respuesta: idRole = 3
```

2. **Asignar módulos**:
```bash
POST http://localhost:8080/admin/roles/3/assign-modules
{
  "roleId": 3,
  "moduleIds": [6, 10]  # Módulos: MENU, REPORTES
}
```

3. **Crear usuario**:
```bash
POST http://localhost:8080/admin/users
{
  "email": "julia@elshovi.com",
  "userName": "julia_chef",
  "password": "Chef1234",
  "fullName": "Julia Pérez",
  "roleIds": [3]
}
```

---

## 🔒 Seguridad y Autenticación

### Requisitos para acceder a /admin:

1. El usuario debe estar **autenticado**
2. El usuario debe tener el rol **ADMIN**
3. El token JWT debe ser válido

### Headers requeridos:
```http
Authorization: Bearer {JWT_TOKEN}
X-Requested-With: XMLHttpRequest
```

---

## 📱 Frontend - Ejemplo de Integración

### En Angular/React, obtener módulos del usuario:

```typescript
// Obtener datos del usuario autenticado
GET /auth/me
// Respuesta incluye: roles[] → modules[]

// Frontend filtra qué opciones del menú mostrar según módulos accesibles
const accessibleModules = user.roles.flatMap(r => r.modules);
```

---

## 🗄️ Base de Datos - Nuevas Tablas

Se crearon las siguientes tablas automáticamente:

```sql
-- Tabla de módulos
CREATE TABLE modulos (
  id_module INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) UNIQUE NOT NULL,
  description VARCHAR(255),
  path VARCHAR(255),
  active BOOLEAN DEFAULT true
);

-- Tabla de relación Role ↔ Module
CREATE TABLE role_module (
  id_role INT,
  id_module INT,
  PRIMARY KEY (id_role, id_module),
  FOREIGN KEY (id_role) REFERENCES rol(id_role),
  FOREIGN KEY (id_module) REFERENCES modulos(id_module)
);
```

---

## ✅ Máximas de Seguridad

1. **Cambiar contraseña del admin**: 
   - Usar `PUT /admin/users/1` después de iniciar
   
2. **No exponer permisos en el frontend**:
   - Validar permisos SIEMPRE en el backend

3. **Resetear módulos al inicio**:
   - Los módulos se crean automáticamente. Si necesitas agregar más, usa el endpoint `/admin/modules`

4. **Auditoría**:
   - Registra todos los cambios de roles/usuarios en los logs
   - Los logs aparecen en consola con prefijo `✓` (éxito) o `⚠` (advertencia)

---

## 🎯 Flujo de Autorización

```
1. Usuario intenta acceder a /orders
        ↓
2. Interceptor valida token JWT
        ↓
3. Sistema extrae roles del usuario
        ↓
4. Valida @PreAuthorize("hasRole('ADMIN')")
        ↓
5. Si tiene rol → Busca módulos del rol
        ↓
6. Si "PEDIDOS" está en moduleIds → Permite acceso
        ↓
7. Si NO → Devuelve 403 Forbidden
```

---

## 📊 DTOs Creados

### CreateUserDTO
```java
{
  email: String,
  userName: String,
  password: String,
  fullName: String,
  roleIds: Integer[]
}
```

### AssignRolesToUserDTO
```java
{
  userId: Integer,
  roleIds: Integer[]
}
```

### AssignModulesToRoleDTO
```java
{
  roleId: Integer,
  moduleIds: Integer[]
}
```

### ModuleDTO
```java
{
  idModule: Integer,
  name: String,
  description: String,
  path: String,
  active: Boolean
}
```

---

## 🔍 Troubleshooting

**P: ¿Por qué no puedo crear un usuario?**
R: Verifica que:
- Estés autenticado como ADMIN
- El email no exista ya
- Los roleIds sean válidos

**P: ¿Los módulos se crean automáticamente?**
R: Sí, al iniciar la app. Si necesitas más, usa `POST /admin/modules`

**P: ¿Cómo cambio la contraseña del admin?**
R: `PUT /admin/users/1` con la nueva contraseña

**P: ¿Puedo tener un usuario con múltiples roles?**
R: Sí, asigna varios roleIds en `POST /admin/users` o usa `POST /admin/users/{id}/assign-roles`

---

## 🚀 Próximos Pasos

1. ✅ Implementar frontend para el panel de admin
2. ✅ Crear vista de permisos más granular (CRUD por módulo)
3. ✅ Auditoría de cambios en BD
4. ✅ Login con 2FA
5. ✅ Histórico de acceso de usuarios

---

**Sistema operativo y listo para producción** ✨

