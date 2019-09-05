package com.gretro.petclinic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(BlockJUnit4ClassRunner.class)
public class SanityTests {

	@Test
	public void TrueShouldNotBeFalse() {
		assertThat(true).isNotEqualTo(false);
	}

}
