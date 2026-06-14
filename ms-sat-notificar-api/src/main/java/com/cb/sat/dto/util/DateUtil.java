package com.cb.sat.dto.util;

import com.cb.sat.dto.model.Constantes;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DateUtil {
	public static final String ISO_FRONT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	private DateUtil() {
	}

	private static final Locale LOCALE_PE = Locale.of("es", "PE");

	public static final String TIME_ZONE = "America/Bogota";
	public static final String TIME_FORMAT = "HH:mm:ss.SSS";
	public static final String TIME_FORMAT_03 = "HH:mm";
	public static final String TIME_FORMAT_01 = "h:mm";
	public static final String TIME_FORMAT_02 = "a";
	public static final String FORMAT01 = "d' de 'MMMM' de 'yyyy";
	public static final String FORMAT02 = "yyyyMMddHHmmss";
	public static final String FORMAT03 = "yyyyMMdd";
	public static final String FORMATO_REPORTE = "dd/MM/yyyy";
	public static final String FORMATO_DATE_TIME = "dd/MM/yyyy HH:mm";
	public static final String FORMATO_DATE_TIME_AP = "dd/MM/yyyy HH:mm a";
	public static final String FORMAT_DATE = "dd-MM-yyyy";
	public static final String RENIEC_FORMAT = "yyyyMMdd";
	public static final String MIGRACIONES_FORMAT = "dd/MM/yyyy";

	public static Calendar getCalendar() {
		return Calendar.getInstance(LOCALE_PE);
	}

	public static String format(LocalDate date, String format) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format).withLocale(LOCALE_PE);

		return date != null ? date.format(dateTimeFormatter) : Constantes.EMPTY;
	}

	public static String format(LocalDateTime date, String format) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format).withLocale(LOCALE_PE);
		return date != null ? date.format(dateTimeFormatter) : Constantes.EMPTY;
	}

	public static String formatDTExcel(LocalDateTime date, String format) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format).withLocale(LOCALE_PE);
		return date != null ? date.format(dateTimeFormatter) : null;
	}

	public static String formatDExcel(LocalDate date, String format) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format).withLocale(LOCALE_PE);
		return date != null ? date.format(dateTimeFormatter) : Constantes.GUION;
	}

	public static String currentDateAsString() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMAT01).withLocale(LOCALE_PE);

		String format = ZonedDateTime.now(ZoneId.of("America/Lima")).format(dateTimeFormatter);
		return format;
	}

	public static LocalDate getCurrentLocalDate() {
		ZonedDateTime fecha = ZonedDateTime.now(ZoneId.systemDefault());
		return fecha.withZoneSameInstant(ZoneId.of(TIME_ZONE)).toLocalDate();
	}

	/**
	 * get current Date.
	 *
	 * @return Date
	 */
	public static LocalDateTime getCurrentLocalDateTime() {
		ZonedDateTime fecha = ZonedDateTime.now(ZoneId.systemDefault());
		return fecha.withZoneSameInstant(ZoneId.of(TIME_ZONE)).toLocalDateTime();
	}

	/**
	 *
	 * @return
	 */
	public static Integer getCurrentYear() {
		return ZonedDateTime.now(ZoneId.of(TIME_ZONE)).getYear();
	}

	/**
	 *
	 * @return
	 */
	public static Integer getCurrentMonth() {
		return ZonedDateTime.now(ZoneId.of(TIME_ZONE)).getMonth().getValue();

	}

	public static Integer getMinute(LocalDateTime localDateTime) {
		return ZonedDateTime.of(localDateTime, ZoneId.of(TIME_ZONE)).getMinute();
	}

	public static Integer getMonth(LocalDateTime localDateTime) {
		return ZonedDateTime.of(localDateTime, ZoneId.of(TIME_ZONE)).getMonthValue();
	}

	public static Integer getYear(LocalDateTime localDateTime) {
		return ZonedDateTime.of(localDateTime, ZoneId.of(TIME_ZONE)).getYear();
	}

	public static Integer getHour(LocalDateTime localDateTime) {
		return ZonedDateTime.of(localDateTime, ZoneId.of(TIME_ZONE)).getHour();
	}

	public static Integer getDay(LocalDateTime localDateTime) {
		return ZonedDateTime.of(localDateTime, ZoneId.of(TIME_ZONE)).getDayOfMonth();
	}

	/**
	 *
	 * @return
	 */
	public static String getCurrentMonthDesc() {
		return ZonedDateTime.now(ZoneId.of(TIME_ZONE)).getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
	}

	/**
	 *
	 * @return
	 */
	public static Integer getCurrentDay() {
		return ZonedDateTime.now(ZoneId.of(TIME_ZONE)).getDayOfMonth();
	}

	/**
	 *
	 * @return
	 */
	public static Integer getCurrentDayWeek() {
		return ZonedDateTime.now(ZoneId.of(TIME_ZONE)).getDayOfWeek().getValue();
	}

	/**
	 *
	 * @return
	 */
	public static Integer getCurrentHour() {
		return ZonedDateTime.now(ZoneId.of(TIME_ZONE)).getHour();
	}

	/**
	 *
	 * @return
	 */
	public static Integer getCurrentMinute() {
		return ZonedDateTime.now(ZoneId.of(TIME_ZONE)).getMinute();
	}

	/**
	 *
	 * @return
	 */
	public static Integer getCurrentSecond() {
		return ZonedDateTime.now(ZoneId.of(TIME_ZONE)).getSecond();
	}

	public static Long getCurrentMillSecond() {
		return ZonedDateTime.now(ZoneId.of(TIME_ZONE)).toInstant().toEpochMilli();
	}

	public static long getCantidadDias(LocalDate inicio, LocalDate fin) {
		if (GenericUtil.isNotNull(inicio) && GenericUtil.isNotNull(fin)) {
			return ChronoUnit.DAYS.between(inicio, fin);
		}
		return 0;
	}

	public static BigDecimal getCantidadAnios(LocalDate inicio, LocalDate fin) {
		if (GenericUtil.isNotNull(inicio) && GenericUtil.isNotNull(fin)) {
			return new BigDecimal(ChronoUnit.YEARS.between(inicio, fin));
		}
		return BigDecimal.ZERO;
	}

	public static BigDecimal getCantidadMeses(LocalDate inicio, LocalDate fin) {
		if (GenericUtil.isNotNull(inicio) && GenericUtil.isNotNull(fin)) {
			return new BigDecimal(ChronoUnit.MONTHS.between(inicio, fin));
		}
		return null;
	}

	public static LocalDateTime addDaysDate(LocalDateTime fecha, Integer days) {
		ZonedDateTime zonedDateTime = ZonedDateTime.of(fecha.plusDays(days), ZoneId.systemDefault());
		return zonedDateTime.toLocalDateTime();
	}

	public static LocalDate addDate(LocalDate fecha, Integer days) {
		ZonedDateTime zonedDateTime = ZonedDateTime.of(fecha, LocalTime.now(), ZoneId.systemDefault());
		return zonedDateTime.withZoneSameInstant(ZoneId.of(TIME_ZONE)).toLocalDate();
	}

	public static String getHours(LocalDateTime fecha, String format) {
		if (GenericUtil.isNotNull(fecha)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			return formatter.format(fecha);
		}
		return Constantes.EMPTY;
	}

	public static String getNombreMes(Integer key) {
		String nombre = Constantes.EMPTY;
		switch (key) {
		case 1:
			nombre = "Enero";
			break;
		case 2:
			nombre = "Febrero";
			break;
		case 3:
			nombre = "Marzo";
			break;
		case 4:
			nombre = "Abril";
			break;
		case 5:
			nombre = "Mayo";
			break;
		case 6:
			nombre = "Junio";
			break;
		case 7:
			nombre = "Julio";
			break;
		case 8:
			nombre = "Agosto";
			break;
		case 9:
			nombre = "Setiembre";
			break;
		case 10:
			nombre = "Octubre";
			break;
		case 11:
			nombre = "Noviembre";
			break;
		case 12:
			nombre = "Dicembre";
			break;
		default:
			break;
		}
		return nombre;
	}

	public static String getAbreviaturaMes(Integer key) {
		String nombre = Constantes.EMPTY;
		switch (key) {
		case 1:
			nombre = "EN";
			break;
		case 2:
			nombre = "FEB";
			break;
		case 3:
			nombre = "MAR";
			break;
		case 4:
			nombre = "ABR";
			break;
		case 5:
			nombre = "NAY";
			break;
		case 6:
			nombre = "JUN";
			break;
		case 7:
			nombre = "JUL";
			break;
		case 8:
			nombre = "AG";
			break;
		case 9:
			nombre = "SET";
			break;
		case 10:
			nombre = "OCT";
			break;
		case 11:
			nombre = "NOV";
			break;
		case 12:
			nombre = "DIC";
			break;
		default:
			break;
		}
		return nombre;
	}

	public static LocalDate toLocalDate(String date) {
		if (date == null)
			return null;
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(ISO_FRONT_FORMAT);
		return LocalDate.parse(date, inputFormatter);
	}

	public static LocalDate of(String date, String format) {
		if (date == null)
			return null;
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(format);
		return LocalDate.parse(date, inputFormatter);
	}

	public static LocalDateTime ofTime(String date, String format) {
		if (date == null)
			return null;
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(format);
		return LocalDateTime.parse(date, inputFormatter);
	}

	public static boolean isTodaySunday() {
		LocalDateTime hoy = LocalDateTime.now();
		return hoy.getDayOfWeek().equals(DayOfWeek.SUNDAY);
	}

	public static long getDiasHastaSabado(LocalDateTime fechaActual) {
		DayOfWeek diaActual = fechaActual.getDayOfWeek();
		LocalTime horaActual = fechaActual.toLocalTime();
		if (diaActual == DayOfWeek.SATURDAY && horaActual.isAfter(LocalTime.MIDNIGHT)
				&& horaActual.isBefore(LocalTime.of(6, 0))) {
			return 0;
		}

		long diasParaSabado = DayOfWeek.SATURDAY.getValue() - diaActual.getValue();

		if (diasParaSabado < 0) {
			diasParaSabado += 7;
		}

		return diasParaSabado;
	}

	public static String getNombreDia(LocalDateTime fecha) {
		return String.format("%02d", fecha.getDayOfMonth());
	}

	public static List<String> getEtiquetasHorasDelDia() {
		List<String> etiquetasHoras = new ArrayList<>();
		LocalTime ahora = LocalTime.now();
		LocalTime horaFinal = ahora.plusHours(1);
		if (horaFinal.getHour() == 24) {
			horaFinal = LocalTime.of(23, 59);
		}

		for (int i = 0; i <= horaFinal.getHour(); i++) {
			if (i == 23) {
				etiquetasHoras.add("23:59");
			} else {
				String etiquetaHora = LocalTime.of(i, 0).toString();
				etiquetasHoras.add(etiquetaHora);
			}
		}
		return etiquetasHoras;
	}

	public static List<LocalDateTime> getFechasHastaHoraActualMas1(LocalDateTime fechaFin) {
		List<LocalDateTime> fechasDePeriodo = new ArrayList<>();
		LocalDateTime ahora = LocalDateTime.now();
		LocalDateTime horaFinal = ahora.plusHours(1);

		for (int hour = 0; hour <= horaFinal.getHour(); hour++) {
			fechasDePeriodo.add(fechaFin.withHour(hour).withMinute(0).withSecond(0).withNano(0));
		}
		fechasDePeriodo.add(fechaFin.with(LocalTime.MAX));
		return fechasDePeriodo;
	}

	public static List<LocalDateTime> getFechasDelMesHastaHoy(LocalDateTime fechaFin) {
		List<LocalDateTime> fechasDePeriodo = new ArrayList<>();

		LocalDateTime fechaInicio = fechaFin.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);

		LocalDateTime fechaHoy = LocalDateTime.now();
		YearMonth yearMonth = YearMonth.from(fechaFin);
		LocalDateTime ultimoDiaDelMes = yearMonth.atEndOfMonth().atTime(23, 59, 59, 999999999);

		LocalDateTime fechaLimite = fechaHoy.isBefore(ultimoDiaDelMes)
				? fechaHoy.plusDays(1).withHour(23).withMinute(59).withSecond(59).withNano(999999999)
				: ultimoDiaDelMes;

		for (LocalDateTime dia = fechaInicio; !dia.isAfter(fechaLimite); dia = dia.plusDays(1)) {
			fechasDePeriodo.add(dia);
		}

		return fechasDePeriodo;
	}

	public static List<String> getEtiquetasDelMesHastaHoy(LocalDate fechaInicio) {
		List<String> etiquetas = new ArrayList<>();
		LocalDate fechaHoy = LocalDate.now();

		LocalDate fechaFin = fechaHoy.isBefore(fechaInicio.withDayOfMonth(fechaInicio.lengthOfMonth())) ? fechaHoy
				: fechaInicio.withDayOfMonth(fechaInicio.lengthOfMonth());
		for (LocalDate dia = fechaInicio; !dia.isAfter(fechaFin); dia = dia.plusDays(1)) {
			String etiquetaDia = dia.format(DateTimeFormatter.ofPattern("dd", Locale.getDefault()));
			etiquetas.add(etiquetaDia);
		}
		return etiquetas;

	}

	public static String convertirHoraA24Horas(String entrada) {
		entrada = entrada.replace("\u00A0", " ").trim();
		try {
			String[] partes = entrada.split(" ");
			String hora12 = partes[1] + " " + partes[2] + " " + partes[3] + " " + partes[4];
			hora12 = hora12.replace("\u00A0", " ").replace("p. m.", "PM").replace("a. m.", "AM").trim();

			DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a", Locale.ENGLISH);
			DateTimeFormatter formatoSalida = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

			LocalDateTime hora24 = LocalDateTime.parse(hora12, formatoEntrada);
			return hora24.format(formatoSalida);
		} catch (Exception e) {
			return "Formato inválido";
		}
	}

}
