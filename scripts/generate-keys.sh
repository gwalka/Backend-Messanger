set -euo pipefail

OUT_DIR="./secrets"

mkdir -p "$OUT_DIR"

echo "Generating RSA 2048 keys into: $OUT_DIR"

# generate private key
openssl genrsa -out "$OUT_DIR/private.pem" 2048

# generate public key
openssl rsa -in "$OUT_DIR/private.pem" -pubout -out "$OUT_DIR/public.pem"

# convert private to PKCS#8 for Java
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt \
  -in "$OUT_DIR/private.pem" -out "$OUT_DIR/private_pkcs8.pem"

# remove old private.pem
rm -f "$OUT_DIR/private.pem"

# permissions
chmod 600 "$OUT_DIR/private_pkcs8.pem"
chmod 644 "$OUT_DIR/public.pem"

echo "Keys generated in $OUT_DIR"

