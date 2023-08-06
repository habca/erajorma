package fi.linna.erajorma.view;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

public class FragmentSerializer {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static <T> T Deserialize(Fragment fragment, String key) throws RuntimeException {
        if (fragment.getArguments() != null) {
            String serializedObject = fragment.getArguments().getString(key);
            byte[] decodedObject = Base64.getDecoder().decode(serializedObject);
            try (ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(decodedObject))) {
                return (T) stream.readObject();
            } catch (ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Arguments missing.");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static <T> void Serialize(Fragment fragment, String key, T value) throws RuntimeException {
        Bundle args = new Bundle();
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(value);
            String encodedObject = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
            args.putSerializable(key, encodedObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fragment.setArguments(args);
    }
}
