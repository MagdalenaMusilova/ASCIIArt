# ASCII Art Converter

A Scala-based image-to-ASCII art converter, created as a semestral project for the *Object-Oriented Programming* course.

This tool allows you to convert images into ASCII art using various filters, output methods, and shading techniques.

---

## Getting Started

### Requirements

* sbt
* java

### Running the App

Launch the interactive sbt shell:

```bash
sbt
```

To convert an image:

```bash
run --image [pathToFile] [options...]
```

**Example:**

```bash
run --image "../loaderTest5.jpg" --rotate +90 --scale 0.25 --invert --output-console
```

For more options and help:

```bash
run --help
```

---

## Command-Line Options

Command order does not matter.

### Image Input (choose one)

* `--image [pathToFile]` — Load an image from file.
* `--image-random` — Generate a random image.

### Output (choose at least one)

* `--output-file [pathToFile]` — Save the ASCII output to a file.
* `--output-console` — Print the ASCII output directly to the console.

### ASCII Shading (choose at most one)

* `--table [tableName]` — Use a predefined shading table.
* `--custom-table [characters]` — Use a custom string of characters for shading.
* `--nonlinear-table [pathToFile]` — Use a non-linear shading table defined in a file.

### Image Filters (optional, can be combined)

* `--rotate [degrees]` — Rotate the image by a specified degree (e.g., 90, -45).
* `--scale [factor]` — Scale the image size (e.g., 0.5 for half size).
* `--invert` — Invert image colors.
* `--flip [axis]` — Flip image along `x` or `y` axis.
* `--brightness [value]` — Adjust brightness (positive or negative).
* `--fix-ratio` — Adjust for the character aspect ratio (1 pixel is 2 characters instead of 1).

### Help

* `--help` — Show all available commands and usage instructions.

---
