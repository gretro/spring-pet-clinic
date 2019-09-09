package com.gretro.petclinic;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SanityTests {
	@Test
	public void TrueShouldNotBeFalse() {
		assertThat(true).isNotEqualTo(false);
	}

}
