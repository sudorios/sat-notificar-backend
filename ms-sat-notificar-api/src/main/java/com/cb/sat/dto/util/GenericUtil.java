package com.cb.sat.dto.util;

import com.cb.sat.dto.model.Constantes;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Base64;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class GenericUtil {

	private GenericUtil() {
	}

	/**
	 * checks if the object is not empty.
	 * 
	 * @param object
	 * @return true o false
	 */
	public static boolean isNotEmpty(Object object) {
		return !isObjectEmpty(object);
	}

	/**
	 * checks if the collection is empty.
	 * 
	 * @param collection
	 * @return true o false
	 */
	public static <E> boolean isEmpty(Collection<E> collection) {
		return (collection == null) || collection.isEmpty();
	}

	/**
	 * checks if the map is empty.
	 * 
	 * @param map
	 * @return true o false
	 */
	public static <K, E> boolean isEmpty(Map<K, E> map) {
		return (map == null) || (map.isEmpty());
	}

	/**
	 * checks if the character is empty.
	 * 
	 * @param character
	 * @return true o false
	 */
	public static boolean isEmpty(CharSequence character) {
		return (character == null) || (character.length() == 0);
	}

	public static boolean isEmptyWithTrim(String character) {
		return (character == null) || (character.trim().length() == 0);
	}

	public static String emptyIfStringNull(String character) {
		if (isEmpty(character)) {
			return Constantes.EMPTY;
		}
		return character;
	}

	public static String CSCIfStringNull(String character) {
		if (isEmpty(character)) {
			return Constantes.CSC;
		}
		return character;
	}

	/**
	 * 
	 * @param value
	 * @return true o false
	 */
	public static boolean isObjectEmpty(Object value) {
		if (value == null) {
			return true;
		} else if (value instanceof String) {
			return isEmpty((String) value);
		} else if (value instanceof CharSequence) {
			return isEmpty((CharSequence) value);
		} else if (value instanceof Collection || value instanceof Map) {
			return isCollectionEmpty(value);
		}
		return false;
	}

	private static boolean isCollectionEmpty(Object value) {
		if (value instanceof Collection) {
			return isEmpty((Collection<? extends Object>) value);
		} else {
			return isEmpty((Map<? extends Object, ? extends Object>) value);
		}
	}

	/**
	 * checks if the object is null.
	 * 
	 * @param object
	 * @return true o false
	 */
	public static boolean isNull(Object object) {
		if (object != null) {
			return false;
		}
		return true;
	}

	/**
	 * checks if the object is not null.
	 * 
	 * @param object
	 * @return true o false
	 */
	public static boolean isNotNull(Object object) {
		if (object != null) {
			return true;
		}
		return false;
	}

	public static String fillZero(Long numero, Integer longuitud) {
		StringBuilder builder = new StringBuilder();
		Integer cantida = longuitud - numero.toString().length();
		for (int i = 0; i < cantida; i++) {
			builder.append(Constantes.CERO);
		}
		builder.append(numero);
		return builder.toString();
	}

	public static int getToken() {
		Random random = new Random();
		return random.ints(100000, (999999 + 1)).findFirst().getAsInt();
	}

	public static int getClaveSMS(int min, int max) {
		Random random = new Random();
		return random.ints(min, (max + 1)).findFirst().getAsInt();
	}

	public static byte[] decodeBase64(String base64Image) {
		if (GenericUtil.isNotEmpty(base64Image)) {
			String partes[] = base64Image.split(",");
			if (partes.length > 1) {
				return java.util.Base64.getDecoder().decode(partes[1]);
			}
		}
		return null;
	}

	public static String toBase64(String ticket) {
		return Base64.getEncoder().encodeToString(ticket.getBytes());
	}

	public static String truncate(String texto, Integer longitud) {
		if (texto.length() <= longitud) {
			return texto.substring(0, texto.length());
		} else {
			return texto.substring(0, longitud);
		}
	}

	public static String getByteImage(String base64) throws IOException {
		if (!isEmptyWithTrim(base64)) {
			String recurso[] = base64.split(",");
			return recurso[1].toString();
		}
		return Constantes.EMPTY;
	}

	public static String fillZero(Integer numero, Integer longuitud) {
		StringBuilder builder = new StringBuilder();
		Integer cantida = longuitud - numero.toString().length();
		for (int i = 0; i < cantida; i++) {
			builder.append(Constantes.CERO);
		}
		builder.append(numero);
		return builder.toString();
	}

	public static String fillMensajeError(int index, String mensaje) {
		StringBuilder builder = new StringBuilder(mensaje);
		builder.append(" - > fila: ");
		builder.append(index + 1);
		return builder.toString();
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>();
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	public static void toUpperCase(Object request) {
		try {
			for (java.lang.reflect.Field field : request.getClass().getDeclaredFields()) {
				if (field.getType().equals(String.class)) {
					if (!field.canAccess(request)) {
						field.trySetAccessible();
					}
					if (field.get(request) != null && !((String) field.get(request)).trim().equals(Constantes.EMPTY)) {
						field.set(request, ((String) field.get(request)).toUpperCase());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String replaceSpaces(String cadena) {
		if (cadena == null) {
			return null;
		}
		return cadena.replace(' ', '_');
	}

    public static String limpiarTelefono(String telefono) {
        if (!StringUtils.hasText(telefono)) {
            return "";
        }
        return telefono.replaceAll("[\\+\\s-]", "");
    }

}
