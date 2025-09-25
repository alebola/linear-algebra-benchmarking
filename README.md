# ⚡ Linear Algebra Benchmarking

This repository contains a benchmarking project for **sparse and dense matrix operations** implemented in Java.  
The goal is to compare performance between different matrix representations and multiplication strategies using **JMH (Java Microbenchmark Harness)**.


## 📂 Project Structure
```
├── pom.xml       # Maven project configuration
├── olm5000.mtx   # Example MatrixMarket input file
├── src/
│ ├── main/java/software/ulpgc/bigdata/algebra/matrices/longint/
│ │ ├── matrix/         # Core matrix classes (DenseMatrix, SparseMatrix, etc.)
│ │ ├── matrixbuilders/ # Builders and converters (CRS, CCS, TiledMatrixMultiplier, etc.)
│ │ └── operators/      # Benchmark operators (Operations)
│ └── test/java/        # Unit tests
```


## 🚀 Features
- Implementation of **dense** and **sparse** matrix structures.  
- Support for **Compressed Row (CRS)** and **Compressed Column (CCS)** formats.  
- **Tiled matrix multiplication** for performance optimization.  
- Benchmarks using **JMH** to measure execution time.  
- Example dataset included (`olm5000.mtx` in MatrixMarket format).  


## 🛠️ Technologies
- **Java 17**  
- **Maven** for build automation  
- **JMH (Java Microbenchmark Harness)** for benchmarking  
- **JUnit 5** for testing  


## ▶️ How to Run
### 1. Build the project
```bash
mvn clean install
```
### 2. Run tests
```bash
mvn test
```
### 3. Run benchmarks
You can run the JMH benchmarks with:
```bash
mvn clean install
java -jar target/benchmarks.jar
```


## 📊 Example Output
Running the benchmarks will produce output similar to:
```bash
Matrix Size: 100x100 - Execution Time: 12 ms
Matrix Size: 200x200 - Execution Time: 85 ms
Matrix Size: 300x300 - Execution Time: 320 ms
```


## 👨‍💻 Author
- Developed by Alejandro Bolaños during my studies in **Data Science and Engineering (ULPGC)**.
- The project focuses on high-performance computing and **linear algebra optimizations**.
