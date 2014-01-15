# Available archetypes

## Multi module archetype

Designed to develop a new Operation divided on core and web extension. To use it:

1. Generate project

`$ mvn archetype:generate -DarchetypeArtifactId=opensdi-multimodule-archetype -DarchetypeGroupId=it.geosolutions.opensdi -DarchetypeVersion=1.0-SNAPSHOT -DartifactId=opensdi-multimodule-example -DgroupId=it.geosolutions.opensdi -Dversion=1.0-SNAPSHOT`

2. Change Operation to ingest some model data: 

```
public String getJsp(ModelMap model, HttpServletRequest request,
        List<MultipartFile> files) {

    // TODO: Include model data

    return "custom";

}
```

3. Use it on the custom.jsp page

NOTE: You must change the class name and the jsp file name and references with something related with your extension