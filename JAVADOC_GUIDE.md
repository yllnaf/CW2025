# Javadoc Documentation Generation Guide

## How to Generate Javadoc

### Using Maven

1. **Generate Javadoc**:
   ```bash
   mvn javadoc:javadoc
   ```
   or using Maven wrapper:
   ```bash
   ./mvnw.cmd javadoc:javadoc
   ```

2. **Location**:
   - The Javadoc is generated in `target/site/apidocs/`
   - A copy has been placed in the `Javadoc/` folder in the project root

### Viewing the Documentation

1. **Open in Browser**:
   - Navigate to `Javadoc/index.html`
   - Open it in any web browser

2. **Main Pages**:
   - `index.html` - Overview and package list
   - `overview-summary.html` - Package overview
   - `allclasses-index.html` - All classes index
   - `search.html` - Search functionality

## Documentation Structure

The Javadoc includes:
- **Class Documentation**: All public classes with descriptions
- **Method Documentation**: All public methods with parameters and return values
- **Package Documentation**: Package-level descriptions
- **Cross-references**: Links between related classes and methods

## Configuration

The Javadoc plugin is configured in `pom.xml` with:
- Source/Target: Java 23
- Encoding: UTF-8
- Window Title: "Tetris Game API Documentation"
- Copyright notice

## Regenerating Documentation

To regenerate the documentation after code changes:
1. Run `mvn javadoc:javadoc`
2. Copy the contents from `target/site/apidocs/` to `Javadoc/`

Or use the provided script:
```bash
mvn javadoc:javadoc
Copy-Item -Recurse -Force "target\site\apidocs\*" "Javadoc\"
```

