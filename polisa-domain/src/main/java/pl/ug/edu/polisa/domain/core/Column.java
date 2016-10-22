package pl.ug.edu.polisa.domain.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Adnotacja do oznaczania nazwy kolummny
 * 
 * @author Michał Hoffmann
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface Column {

	/**
	 * Nazwa tabeli
	 * 
	 * @return
	 */
	String name() default "";
}
