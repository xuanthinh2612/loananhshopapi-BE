//package loananhshop.api.mapper;
//
//
//import springReact.StudentManagement.dto.SchoolClassDto;
//import springReact.StudentManagement.model.SchoolClass;
//
//public class SchoolClassMapper {
//    public static SchoolClassDto mapToSchoolClassDto(SchoolClass schoolClass) {
//        return new SchoolClassDto(
//                schoolClass.getId(),
//                schoolClass.getName(),
//                schoolClass.getDescription()
//        );
//    }
//
//    public static SchoolClass mapToSchoolClass(SchoolClassDto schoolClassDto) {
//        return new SchoolClass
//                (
//                        schoolClassDto.getId(),
//                        schoolClassDto.getName(),
//                        schoolClassDto.getDescription()
//                );
//    }
//}



//    private User mapUserDtoToUser(UserDto userDto) {
//        User user = new User();
//        String[] str = user.getName().split(" ");
//        user.setFirstName(str[0]);
//        user.setLastName(str[1]);
//        user.setEmail(user.getEmail());
//        user.setPhoneNumber(user.getPhoneNumber());
//        user.setAddress(user.getAddress());
//        user.setId(user.getId());
//        user.setAge(user.getAge());
//        user.setGender(user.getGender());
//        user.setActiveStatus(user.isActiveStatus());
//        user.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
//        return userDto;
//    }