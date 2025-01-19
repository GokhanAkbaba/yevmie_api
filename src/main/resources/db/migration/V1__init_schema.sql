-- Kullanıcılar tablosu
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    city VARCHAR(50),
    company VARCHAR(100),
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Roller tablosu
CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

-- Kullanıcı rolleri tablosu
CREATE TABLE user_roles (
    user_id BIGINT REFERENCES users(id),
    role_id BIGINT REFERENCES roles(id),
    PRIMARY KEY (user_id, role_id)
);

-- Şantiyeler tablosu
CREATE TABLE santiyeler (
    id BIGSERIAL PRIMARY KEY,
    santiye_adi VARCHAR(100) NOT NULL,
    il VARCHAR(50),
    toplam_harcanan DECIMAL(15,2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Cariler tablosu
CREATE TABLE cariler (
    id BIGSERIAL PRIMARY KEY,
    cari_adi VARCHAR(236) NOT NULL,
    il VARCHAR(50),
    santiye_id BIGINT REFERENCES santiyeler(id),
    tur VARCHAR(50),
    tutar DECIMAL(15,2),
    aciklama TEXT,
    ekleyen_kullanici_id BIGINT REFERENCES users(id),
    ekleme_tarihi TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Gelirler tablosu
CREATE TABLE gelirler (
    id BIGSERIAL PRIMARY KEY,
    tarih DATE NOT NULL,
    aciklama TEXT,
    tutar DECIMAL(15,2) NOT NULL,
    para_birimi VARCHAR(10) NOT NULL,
    ekleyen_kullanici_id BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Giderler tablosu
CREATE TABLE giderler (
    id BIGSERIAL PRIMARY KEY,
    tarih DATE NOT NULL,
    cari_id BIGINT REFERENCES cariler(id),
    aciklama TEXT,
    miktar DECIMAL(15,3),
    birim VARCHAR(20),
    birim_fiyat DECIMAL(15,2),
    para_birimi VARCHAR(10) NOT NULL,
    tutar DECIMAL(15,2) NOT NULL,
    ekleyen_kullanici_id BIGINT REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Audit log tablosu
CREATE TABLE audit_logs (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50),
    class_name VARCHAR(100),
    method_name VARCHAR(100),
    timestamp TIMESTAMP,
    success BOOLEAN,
    error_message TEXT
); 