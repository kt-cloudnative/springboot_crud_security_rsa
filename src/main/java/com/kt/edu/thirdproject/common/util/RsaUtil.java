package com.kt.edu.thirdproject.common.util;

import com.kt.edu.thirdproject.common.exception.CustomEduException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


@Slf4j
@Component
public class RsaUtil {

    private static final String base64PublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjraOu8op0Bgf1LTMD8kTqMz/iI7/y6E7/Hc486akEggP7CsH2xZdhBnjR6BySpNaMLqrpeT9p141b3DLVsHLuIPpqvEcvjglFynwj9+IjB3vZg5cEvfrR0WBy+AhmWL2bQDxivZ0k8f0tDTBVmZI+LLLat22ncwYZknBpa+DePVkBu618yBWi8t9Bs5OKiOpv+yVKGO7vfxuSLC+eGdU3eOd+PpdvdpRMormJ56y8XJKOMUt4YJi5JApI/x8sJ6K7cH2o+5nXqSluBfZ+eRczuwIFj8qkxES6eoEFUy4n/yC1xFM6GmP20sLlVHKp527jCUvyR7m4dB+lcU0QN/powIDAQAB";
    private static final String base64PrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCOto67yinQGB/UtMwPyROozP+Ijv/LoTv8dzjzpqQSCA/sKwfbFl2EGeNHoHJKk1owuqul5P2nXjVvcMtWwcu4g+mq8Ry+OCUXKfCP34iMHe9mDlwS9+tHRYHL4CGZYvZtAPGK9nSTx/S0NMFWZkj4sstq3badzBhmScGlr4N49WQG7rXzIFaLy30Gzk4qI6m/7JUoY7u9/G5IsL54Z1Td4534+l292lEyiuYnnrLxcko4xS3hgmLkkCkj/Hywnortwfaj7mdepKW4F9n55FzO7AgWPyqTERLp6gQVTLif/ILXEUzoaY/bSwuVUcqnnbuMJS/JHubh0H6VxTRA3+mjAgMBAAECggEBAIYIgO9wBGszbRnJn+mncPZVah1oKGeoC32mv29eb7ObrNCi7WLfk37RDXuk/hJ6Z/diKnnJIk3BljAO4Hqf0mR+R8RLZTJl6rfhpNSLHWRzbPcNv331KBys9CV1YDj+D/D6NODNbVmqL3HcJkSuFhS1X4UDrFJxb+0Jkny45aKAdmdc1u/ZMJ5WqC5HEokQFvl4ID2Mb8qoaW4M4Rip0PXMLBARCO352WN8YJl04NHVQzBxFFSgXiJE2WFX4eIw8o/SjbViDCmuCWEJVrMIdwkaLuFXsiVRG8jEntKLg25q67M/ksfsVMTiKAQwh5469vLAAMqhXRlNd4NxsY5vEPECgYEAxixutUpCau+lJmERwPi4WB02RiOTgL+URuJjl6kskGG52SXrkJYX7C5FLYvGiLtlujoC+RW2JPXxYkQpBTSCxaiNYyYvxHfxYzOfMIkM5TSjTbWSe6H7lT6WYHQPbE/SNaglB4+Mk9lxOItT7vD6aGvWzDUbpSVrJWkcJ2D1lukCgYEAuFs6mxpNlmYyMr3hHYxglX5290qI3LSUv3s0OJVdFyVHJUDfG8AyhyqFWPUPda0N99rFw2TTp484yI3HADVUJzCyNI38t71ab81Q31NBxly4Kx8rbndsa8GcGgT5eqNYybDkIq2zfnVJ2O9t0925KNZNd7GAHqz3nTB/lnNZvKsCgYBn7StN1ZjJPc4NN4C5A3ahdwhcdhHHxjB7kAQMqAKf4TA2qcVEuQHosUdEIHZIhIZPyFaAzBsOfRBOqVId0bD2+XLUWVVZolEr5gmIyYbCuP0fJqagQY2PchgokxT3h1DFJN3ufiCJ9NGEhsTInQWlN1CZHQzXaa0sHFhQam/A6QKBgCNPzhzxX9UkBA92EU2Amutnis/5JdfoeulmZu1Dps4NJOB82X9ORB9hxKLyZaCrohZMgD7Z7DSXGVBI38opbNII4MlyPOrQySbLE+eC4gFltx0DXmVNWKYXMphFRaf468POLxR5Yv0g6CE2UzoWqSWqTrJTfG/jv5YLORUPSSv7AoGBAIDDfTfFO+QB6A4/NhNV69XOW0etyVRNML+Pm0g+PWoPC4rQEdIlntMk+RQIp4+uoyaSEV6YKpHKshYoLeIfPyxwYi4Q/mdDwNfQGbX+FO+zseKzLyEwI5UVvq08gSlxzu+U81vMqDWgevz5Yh7MU9cpmVN24NzEm7FJOKfVmIp4";

    public static String encryptRSA(String plainText, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] bytePlain = cipher.doFinal(plainText.getBytes());

        String encrypted = Base64.getEncoder().encodeToString(bytePlain);
        return encrypted;

    }

    public static String decryptRSA(String encrypted, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance("RSA");
        byte[] byteEncrypted = Base64.getDecoder().decode(encrypted.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytePlain = cipher.doFinal(byteEncrypted);
        String decrypted = new String(bytePlain, StandardCharsets.UTF_8);
        return decrypted;
    }

    public static PublicKey getPublicKeyFromBase64Encrypted(String base64PublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {

        byte[] decodedBase64PubKey = Base64.getDecoder().decode(base64PublicKey);
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decodedBase64PubKey));
    }


    public static PrivateKey getPrivateKeyFromBase64Encrypted(String base64PrivateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {

        byte[] decodedBase64PrivateKey = Base64.getDecoder().decode(base64PrivateKey);
        return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decodedBase64PrivateKey));

    }

   /* public static PrivateKey getPrivateKeyPkcs1FromBase64Encrypted(String base64PrivateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {

        DerInputStream derReader = new DerInputStream(Base64.getDecoder().decode(base64PrivateKey));
        DerValue[] seq = derReader.getSequence(0);

        RSAPrivateCrtKeySpec keySpec =
                new RSAPrivateCrtKeySpec(seq[1].getBigInteger(), seq[2].getBigInteger(), seq[3].getBigInteger(), seq[4].getBigInteger(), seq[5].getBigInteger(), seq[6].getBigInteger(), seq[7].getBigInteger(), seq[8].getBigInteger());

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }*/

    public static String passwordDescryptRSA(String encrypted) {

        try {
            //PrivateKey privateKey = getPrivateKeyPkcs1FromBase64Encrypted(base64PrivateKey);
            PrivateKey privateKey = getPrivateKeyFromBase64Encrypted(base64PrivateKey);
            return decryptRSA(encrypted, privateKey);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            log.error("알 수 없는 암호화 알고리즘입니다. 암호화 하지 않습니다.", e);
            throw new CustomEduException("알 수 없는 암호화 알고리즘입니다. 암호화 하지 않습니다.", e);
        } catch (InvalidKeyException | InvalidKeySpecException e) {
            log.error("Key 초기화 오류 입니다. 암호화 하지 않습니다.", e);
            throw new CustomEduException("Key 초기화 오류 입니다. 암호화 하지 않습니다.", e);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            log.error("암호화 오류 입니다. 암호화 하지 않습니다.", e);
            throw new CustomEduException("암호화 오류 입니다. 암호화 하지 않습니다.", e);
        }
    }
}