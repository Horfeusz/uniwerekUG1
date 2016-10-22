package pl.ug.edu.polisa.domain.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Adnotacja w której będziemy podawać nazwę tabeli dla encji
 * 
 * @author Michał Hoffmann
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {

	/**
	 * Nazwa tabeli
	 * 
	 * @return
	 */
	String tableName() default "";
}
