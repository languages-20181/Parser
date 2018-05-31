package languages.util;
import java.io.File;

public class JavaFilter extends AbstractLangaugeFilter
{
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = getExtension(f);
        if (extension != null) {
            if (extension.equals("java")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
