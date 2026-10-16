-- ====================================================================
-- COMPLEMENTO DE TABLAS PARA SUPABASE (CINÉPOLIS)
-- Ejecutar este script en el SQL Editor de Supabase para habilitar:
-- 1. Catálogo de Dulcería y Combos (RF06)
-- 2. Sistema de Reservas, Compras y Boletos QR (RF07, RF08)
-- ====================================================================

-- 1. TABLA DULCERÍA / CONFITERÍA
CREATE TABLE IF NOT EXISTS public.dulceria_producto (
    id_producto BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio NUMERIC(10, 2) NOT NULL,
    categoria VARCHAR(50), -- COMBO, PALOMITAS, BEBIDAS, SNACKS
    imagen_url TEXT,
    disponible BOOLEAN DEFAULT TRUE
);

-- Insertar datos de prueba para Dulcería
INSERT INTO public.dulceria_producto (nombre, descripcion, precio, categoria, imagen_url, disponible)
VALUES
('Combo Pareja Clásico', '2 Bebidas grandes + 1 Palomitas de maíz gigantes para compartir', 28.50, 'COMBO', 'https://images.unsplash.com/photo-1572177191856-3cde618dee1f?w=600', true),
('Combo Amigos Mega', '4 Bebidas medianas + 2 Palomitas grandes + 1 Nachos con queso cheddar', 52.00, 'COMBO', 'https://images.unsplash.com/photo-1585647347384-2593bc35786b?w=600', true),
('Palomitas Mantequilla Gigante', 'Palomitas recién horneadas con baño de mantequilla especial', 16.00, 'PALOMITAS', 'https://images.unsplash.com/photo-1578849278619-e73505e9610f?w=600', true),
('Nachos con Queso Cheddar Caliente', 'Crujientes totopos con salsa de queso cheddar fundido y jalapeños', 14.50, 'SNACKS', 'https://images.unsplash.com/photo-1513456852971-30c0b8199d4d?w=600', true),
('Gaseosa Grande 32oz', 'Bebida helada a elección (Coca Cola, Inca Kola, Sprite o Fanta)', 10.50, 'BEBIDAS', 'https://images.unsplash.com/photo-1622483767028-3f66f32aef97?w=600', true)
ON CONFLICT DO NOTHING;

-- 2. TABLA RESERVA
CREATE TABLE IF NOT EXISTS public.reserva (
    id_reserva BIGSERIAL PRIMARY KEY,
    codigo_reserva VARCHAR(50) UNIQUE NOT NULL,
    cliente_nombre VARCHAR(100) NOT NULL,
    cliente_email VARCHAR(150) NOT NULL,
    id_funcion BIGINT NOT NULL REFERENCES public.funcion_horario(id_funcion),
    total NUMERIC(10, 2) NOT NULL,
    estado VARCHAR(30) DEFAULT 'CONFIRMADA', -- CONFIRMADA, CANCELADA, PENDIENTE
    codigo_qr TEXT,
    codigo_pickup VARCHAR(20),
    metodo_pago VARCHAR(50),
    fecha_creacion TIMESTAMPTZ DEFAULT NOW()
);

-- 3. TABLA DETALLE DE RESERVA
CREATE TABLE IF NOT EXISTS public.detalle_reserva (
    id_detalle BIGSERIAL PRIMARY KEY,
    id_reserva BIGINT NOT NULL REFERENCES public.reserva(id_reserva) ON DELETE CASCADE,
    id_butaca BIGINT REFERENCES public.butaca(id_butaca),
    tipo_item VARCHAR(30) NOT NULL, -- BUTACA, DULCERIA
    descripcion_item VARCHAR(150) NOT NULL,
    cantidad INT NOT NULL DEFAULT 1,
    precio_unitario NUMERIC(10, 2) NOT NULL,
    subtotal NUMERIC(10, 2) NOT NULL
);
