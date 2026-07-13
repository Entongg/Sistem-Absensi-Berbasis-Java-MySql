# MyAbsensi — Sistem Absensi Sekolah (Desktop)

Aplikasi desktop Java (Swing) untuk manajemen absensi sekolah dengan fitur lengkap:

- Manajemen data guru & siswa (CRUD, validasi, soft delete)
- Sistem absensi digital
- Pelaporan otomatis
- Multi-role (Admin / User)
- Database MySQL
- Implementasi materi UTS / POST-UTS: exception handling, enkapsulasi, Collection Framework

Versi: 1.0.0  
Lisensi: MIT © 2024 Kelompok Cepuluh

---

## Fitur Utama

- Autentikasi pengguna (role-based)
- Dashboard lengkap dengan sidebar, header, dan real-time clock
- Manajemen Guru: tabel, pencarian real-time, modal input, validasi NIP unik, soft delete
- Notifikasi (toast): success, info, warning, error
- Ekspor data (potensial)
- Penerapan Hashing Password (BCrypt) dan penanganan exception terstruktur

---

## Prasyarat

- JDK 8+
- Maven 3.6+
- MySQL 5.7+ atau 8.0+
- (Opsional) IDE: IntelliJ IDEA / NetBeans

---

## Instalasi & Menjalankan (Singkat)

1. Clone repository:

```bash
git clone https://github.com/username/myabsensi.git
cd myabsensi
```

2. Siapkan database:

- Buat database `db_absensi` (atau nama lain sesuai konfigurasi)
- Import skema:

```bash
mysql -u root -p < database/schema.sql
```

3. Konfigurasi koneksi database:

- Buat file `src/main/resources/application.properties` (atau gunakan variable environment). Contoh file template: `application.properties.example` (jangan commit kredensial sensitif).

```properties
db.url=jdbc:mysql://localhost:3306/db_absensi?useSSL=false&serverTimezone=UTC
db.user=root
db.pass=password
# db.driver=com.mysql.cj.jdbc.Driver
```

Atau gunakan environment variables:

- DB_URL, DB_USER, DB_PASS

Catatan keamanan: Jangan menyimpan password nyata di repository. Gunakan file `.gitignore` untuk mengecualikan `application.properties` dan simpan template `application.properties.example` tanpa password.

4. Build:

```bash
mvn clean package
```

5. Jalankan:

```bash
java -jar target/myabsensi-1.0.0.jar
```

6. Login default (hanya untuk demo / setelah registrasi pertama):

```
Username: admin
Password: password123
```

Catatan: Ganti password default segera setelah instalasi. Pastikan UserDAO menyimpan password sebagai hash (BCrypt).

---

## Struktur Konfigurasi yang Direkomendasikan

- src/main/resources/application.properties.example — template konfigurasi (tanpa credentials)
- src/main/resources/application.properties — file runtime (tidak di-commit)
- Gunakan environment variables di server/CI
- Pertimbangkan secret manager (Vault, GitHub Secrets) untuk production

---

## Tips Developer & Rekomendasi Perbaikan

- Gunakan connection pool (HikariCP) untuk performa produksi.
- Migrasi skema database ke Flyway atau Liquibase untuk versioned migrations.
- Pastikan semua password di-hash (BCrypt) dan pengecekan dilakukan dengan BCrypt.checkpw().
- Tambahkan logging terstruktur (slf4j + Logback) — jangan log password.
- Tambahkan unit tests untuk DAO dan service layer.
- Tambahkan script `start-dev` (Maven profile) dan dokumentasi environment variables di README.

---

## Troubleshooting Singkat

- Koneksi gagal: periksa `db.url`, `db.user`, `db.pass`, dan apakah MySQL menerima koneksi dari host aplikasi.
- Driver JDBC tidak ditemukan: pastikan dependency MySQL Connector/J ada di pom.xml.
- Error startup: periksa log (console) untuk stacktrace dan pastikan file `application.properties` tersedia atau env vars ter-set.

---

Dokumentasi lebih lengkap: buka folder `/docs` atau README tambahan di repository.

---

Terima kasih telah menggunakan MyAbsensi
