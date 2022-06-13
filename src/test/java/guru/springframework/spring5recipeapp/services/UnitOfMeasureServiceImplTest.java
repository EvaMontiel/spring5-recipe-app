package guru.springframework.spring5recipeapp.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import guru.springframework.spring5recipeapp.commands.UnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.spring5recipeapp.domain.UnitOfMeasure;
import guru.springframework.spring5recipeapp.repositories.UnitOfMeasureRepository;

public class UnitOfMeasureServiceImplTest {
	
	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;
	
	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	
	UnitOfMeasureService unitOfMeasureService;
	

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
	}

	@Test
	public void testListAllUoms() {
		//given
		Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
		
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId(1L);
		unitOfMeasures.add(uom1);
		
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId(2L);
		unitOfMeasures.add(uom2);
		
		when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);
		
		//when
		Set<UnitOfMeasureCommand> commands = unitOfMeasureService.listAllUoms();
		
		//then
		assertEquals(2, commands.size());
		verify(unitOfMeasureRepository, times(1)).findAll();
	}

}
