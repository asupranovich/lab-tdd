package itechart.tdd.user.service.impl;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import itechart.tdd.user.dao.UserDao;
import itechart.tdd.user.model.User;
import itechart.tdd.user.model.UserNotFoundException;
import itechart.tdd.user.service.UserService;

@RunWith(EasyMockRunner.class)
public class UserServiceImplTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    private UserDao userDao;

    @TestSubject
    private UserService userService = new UserServiceImpl();

    @Test
    public void whenUserExistsReturnIt() throws Exception {

        User user = new User();
        user.setId(5L);
        user.setFirstName("Fn");
        user.setLastName("Ln");

        EasyMock.expect(userDao.getById(5L)).andReturn(user);
        EasyMock.replay(userDao);

        User returnedUser = userService.getById(5L);

        Assert.assertEquals(user, returnedUser);

        EasyMock.verify(userDao);
    }

    @Test
    public void whenUserDoesNotExistReturnNull() throws Exception {

        EasyMock.expect(userDao.getById(5L)).andThrow(new UserNotFoundException());
        EasyMock.replay(userDao);

        User returnedUser = userService.getById(5L);

        Assert.assertNull(returnedUser);

        EasyMock.verify(userDao);
    }

    @Test
    public void whenUserIdNullThrowIllegalArgException() throws Exception {

        EasyMock.replay(userDao);
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("User id should not be null");

        userService.getById(null);

        EasyMock.verify(userDao);
    }

    @Test
    public void whenNewUserThenCreateIt() {
        User user = new User();
        user.setFirstName("Fn");
        user.setLastName("Ln");

        userDao.create(user);
        EasyMock.expectLastCall();
        EasyMock.replay(userDao);

        userService.save(user);

        EasyMock.verify(userDao);
    }

    @Test
    public void whenUserExistsThenUpdateIt() {
        User user = new User();
        user.setId(5L);
        user.setFirstName("Fn");
        user.setLastName("Ln");

        userDao.update(user);
        EasyMock.expectLastCall();
        EasyMock.replay(userDao);

        userService.save(user);

        EasyMock.verify(userDao);
    }

    @Test
    public void whenUserIsNullThrowIllegalArgException() {
        EasyMock.replay(userDao);
        exception.expect(IllegalArgumentException.class);

        userService.save(null);

        EasyMock.verify(userDao);
    }

}
