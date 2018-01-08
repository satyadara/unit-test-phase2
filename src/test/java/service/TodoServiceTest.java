package service;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import springboot.model.Todo;
import springboot.model.constants.TodoPriority;
import springboot.repository.TodoRepository;
import springboot.service.TodoService;

import java.util.ArrayList;
import java.util.List;

public class TodoServiceTest {
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.todoService = new TodoService(this.todoRepository);
    }

    @After
    public void tearDown() {
        //memastikan hanya menggunakan todorepository
        Mockito.verifyNoMoreInteractions(todoRepository);
    }

    @Test
    public void getAllTest() throws Exception {
        // given
        // todo repo must return non empty list when getAll is called
        ArrayList<Todo> todos = new ArrayList<Todo>();
        todos.add(new Todo("todo1", TodoPriority.MEDIUM));
        BDDMockito.given(todoRepository.getAll()).willReturn(todos);

        // when
        List<Todo> result = todoService.getAll();

        //then
        //assert that todo list is not null
        Assert.assertThat(result, Matchers.notNullValue());
        //assert that todo list is not empty
        Assert.assertThat(todos.isEmpty(), Matchers.equalTo(false));
        //verify
        BDDMockito.then(todoRepository).should().getAll();
    }

    @Test
    public void saveTodoTest() {
        //given
        // todo repo must return true when saveTodo is called
        Todo todo = new Todo("myTodo", TodoPriority.MEDIUM);
        BDDMockito.given(todoRepository.store(todo)).willReturn(true);

        //when
        boolean result = todoService.saveTodo(todo.getName(), todo.getPriority());

        //then
        //assert that todo is return true
        Assert.assertThat(result, Matchers.equalTo(true));
        //verify
        BDDMockito.then(todoRepository).should().store(todo);
    }

}
