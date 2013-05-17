package com.example.json;

// TODO: Auto-generated Javadoc
/**
 * The Enum Movie.
 */
public enum Movie {

	/* QUESTION: Because these needed to be exactly how the JSON was it ended up giving me ugly strings.
	 * I know how to hard code better strings but I'm not sure of the right way to dynamically do it.
	 * Any thoughts?
	 */
	
	/** The Title. */
	Title ("Ong-Bak (Ong Bak: Muay Thai Warrior)"),
	
	/** The Year. */
	Year ("2005"),
	
	/** The MPAA Ratings. */
	MPAA_Ratings ("PG-13"),
	
	/** The Movie Run time. */
	RunTime ("107"),
	
	/** The Critics_ consensus. */
	Critics_Consensus ("While Ong-Bak: The Thai Warrior may be no great shakes as a movie, critics are hailing the emergence of a new star in Tony Jaa, whose athletic performance is drawing comparisons with Bruce Lee, Jackie Chan, and Jet Li.");
	
	/** The value. */
	private final String value;
	
	/**
	 * Instantiates a new movie.
	 *
	 * @param value of the ENUM
	 */
	Movie(String value)
	{
		this.value = value;
	}
	
	/**
	 * Sets the value.
	 *
	 * @return the value of the ENUM
	 */
	public String setValue()
	{
		return value;
	}
}
