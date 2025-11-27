Proyecto POSinnovate-Java (consola)

Requisitos:
- Java JDK 8+

Compilación (Linux/Mac):
  cd POSinnovate-Java
  javac -d out $(find src/main/java -name "*.java")
  java -cp out posinnovate.Main

Compilación (Windows PowerShell):
  cd POSinnovate-Java
  $files = Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName }
  javac -d out $files
  java -cp out posinnovate.Main

Flujo básico:
- Al iniciar no existe admin, así que elija la opción "Registrar Admin".
- Luego inicie sesión con el admin.
- Cree roles, usuarios y use los módulos de Usuarios, Inventario, Ventas y Finanzas.
