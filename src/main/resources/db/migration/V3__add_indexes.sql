-- Performans için indeksler
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_cariler_santiye_id ON cariler(santiye_id);
CREATE INDEX idx_gelirler_tarih ON gelirler(tarih);
CREATE INDEX idx_giderler_tarih ON giderler(tarih);
CREATE INDEX idx_giderler_cari_id ON giderler(cari_id);
CREATE INDEX idx_audit_logs_timestamp ON audit_logs(timestamp);

-- Tarih kontrolü için check constraint
ALTER TABLE gelirler ADD CONSTRAINT check_gelir_tarih 
    CHECK (tarih <= CURRENT_DATE);
ALTER TABLE giderler ADD CONSTRAINT check_gider_tarih 
    CHECK (tarih <= CURRENT_DATE);

-- Para birimi kontrolü
ALTER TABLE gelirler ADD CONSTRAINT check_gelir_para_birimi 
    CHECK (para_birimi IN ('TRY', 'USD', 'EUR'));
ALTER TABLE giderler ADD CONSTRAINT check_gider_para_birimi 
    CHECK (para_birimi IN ('TRY', 'USD', 'EUR')); 