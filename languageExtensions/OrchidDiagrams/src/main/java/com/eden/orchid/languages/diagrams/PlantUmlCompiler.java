package com.eden.orchid.languages.diagrams;

import com.eden.orchid.api.compilers.OrchidCompiler;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

@Singleton
public class PlantUmlCompiler extends OrchidCompiler {

    private String[] tags = new String[] {
            "uml",
            "salt",
            "math",
            "latex",
            "gantt"
    };


    @Inject
    public PlantUmlCompiler() {
        super(800);
    }

    @Override
    public String compile(String extension, String source, Map<String, Object> data) {
        try {
            try {
                // ensure string is wrapped in @startuml...@enduml
                source = source.trim();
                source = wrapDiagram(source);

                // compile string to SVG
                ByteArrayOutputStream os = new ByteArrayOutputStream(1024);

                FileFormatOption fileFormat = new FileFormatOption(FileFormat.SVG);
                fileFormat.hideMetadata();

                SourceStringReader reader = new SourceStringReader(source);
                reader.outputImage(os, fileFormat);
                os.close();

                String s = new String(os.toByteArray(), Charset.forName("UTF-8"));
                s = s.replaceAll("<\\?(.*)\\?>", ""); // remove XML declaration

                return s;
            }
            catch (IOException e) {
                e.printStackTrace();
                return "";
            }
        }
        catch (Throwable e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public String getOutputExtension() {
        return "svg";
    }

    @Override
    public String[] getSourceExtensions() {
        return new String[]{"uml"};
    }

    private String wrapDiagram(String input) {
        boolean isWrapped = false;

        String source = input;

        for(String tag : tags) {
            if(source.startsWith("@start" + tag)) {
                isWrapped = true;
                break;
            }
            if(source.endsWith("@end" + tag)) {
                isWrapped = true;
                break;
            }
        }

        if(!isWrapped) {
            source = "@startuml\n" + source;
            source = source + "\n@enduml";
        }

        return source;
    }
}
