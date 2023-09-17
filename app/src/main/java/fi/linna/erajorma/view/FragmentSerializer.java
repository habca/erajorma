package fi.linna.erajorma.view;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

public final class FragmentSerializer {

    private FragmentSerializer() {
        // Prevents instantiation.
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static <T extends Serializable> T Deserialize(String serializedObject) throws RuntimeException {
        byte[] decodedObject = Base64.getDecoder().decode(serializedObject);
        try (ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(decodedObject))) {
            return (T) stream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static <T extends Serializable> String Serialize(T value) throws RuntimeException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {

            objectOutputStream.writeObject(value);
            String encodedObject = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            return encodedObject;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
