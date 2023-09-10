package fi.linna.erajorma.view;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

import fi.linna.erajorma.model.Information;

public final class FragmentSerializer {

    private FragmentSerializer() {
        // Prevents instantiation.
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Information Deserialize(Fragment fragment, String key) throws RuntimeException {
        if (fragment.getArguments() != null) {
            String serializedObject = fragment.getArguments().getString(key);
            byte[] decodedObject = Base64.getDecoder().decode(serializedObject);
            try (ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(decodedObject))) {
                return (Information) stream.readObject();
            } catch (ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Arguments missing.");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Information Deserialize(String serializedObject) throws RuntimeException {
        byte[] decodedObject = Base64.getDecoder().decode(serializedObject);
        try (ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(decodedObject))) {
            return (Information) stream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void Serialize(Fragment fragment, String key, Information value) throws RuntimeException {
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String Serialize(Information value) throws RuntimeException {
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
