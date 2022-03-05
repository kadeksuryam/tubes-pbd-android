# Tugas Besar 1 IF3210 Kelompok 40: Perlu Dilindungi

## Cara Kerja

1. Ketika membuka aplikasi, akan langsung terlihat daftar berita. Aplikasi akan langsung me-load berita, lalu ketika sudah selesai akan ditampilkan
1. Jika salah satu card ditekan, maka akan dibuka webview untuk menampilkan isi berita
1. Kemudian di navigation bar ada menu "Faskes" untuk mencari fasilitas kesehahtan (fragment ini memiliki desain yang responsif)
1. Untuk mencari fasilitas kesehatan, pertama-tama cari provinsi kemudian cari kota
1. Lalu, jika salah satu card fasilitas kesehatan ditekan, kita akan melihat detailnya
1. Di detail ini kita juga dapat mem-bookmark fasilitas kesehatan atau mencari lokasinya di Google Maps
1. Menu selanjutnya di navigation bar adalah "Bookmark" yang berisi fasilitas kesehatan yang sudah di-bookmark
1. Menu "Bookmark" sama dengan menu "Faskes", hanya tidak perlu mencari provinsi dan kota
1. Menu terakhir adalah "Check-in", ketika menu ini ditekan akan membuka activity baru yang dapat menge-scan QR code
1. Jika scan berhasil (hasilnya hijau), akan dikembalikan ke menu berita; selain itu akan dikembalikan ke menu "Check-in"

## Library yang Digunakan

- **Room**: untuk DAO dan menyederhanakan akses ke database
- **Glide**: untuk membantu load gambar pada card berita. Memudahkan mengambil gambar dari URL dan "memasangnya" ke sebuah card
- **Retrofit**: memudahkan pembuatan HTTP request ke API karena dapat langsung me-"retrofit" response HTTP request ke sebuah object

## Screenshots

### Berita

- List

[![List](./screenshots/berita_list.jpg =x500)](./screenshots/berita_list.jpg)

- Webview

[![Webview](./screenshots/berita_webview.jpg =x500)](./screenshots/berita_webview.jpg)

### Search Fasilitas Kesehatan

- Start (blank)

[![Blank](./screenshots/search_faskes_start.jpg =x500)](./screenshots/search_faskes_start.jpg)

- List

[![List](./screenshots/search_faskes_list.jpg =x500)](./screenshots/search_faskes_list.jpg)

### Bookmarks

- Empty

[![Empty](./screenshots/bookmarks_empty.jpg =x500)](./screenshots/bookmarks_empty.jpg)

- Filled

[![Filled](./screenshots/bookmarks_filled.jpg =x500)](./screenshots/bookmarks_filled.jpg)

### Scan

- Start

[![Start](./screenshots/scan.jpg =x500)](./screenshots/scan.jpg)

- Scan result: green

[![Start](./screenshots/scan_result_green.jpg =x500)](./screenshots/scan_result_green.jpg)

- Start result: red

[![Start](./screenshots/scan_result_red.jpg =x500)](./screenshots/scan_result_red.jpg)

## Pembagian Tugas

| NIM | Pekerjaan |
| - | - |
| 13519151 | Kode UI fragment search faskes dan detail faskes, menghubungkan dengan API untuk mencari provinsi, kota, dan faskes |
| 13519164 | Desain UI/UX, Navigation bar, Semua yang berhubungan dengan berita (API, recycler view, webview, fragments, etc.), recycler view adapter untuk list faskes, bookmark/unbookmark, README |
| 13519165 | Semua yang berhubungan dengan check-in (APIs, scanners, sensors, activities, etc.) |
