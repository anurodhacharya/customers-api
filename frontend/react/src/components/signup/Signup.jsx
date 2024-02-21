'use client'

import {
  Flex,
  Text,
  Heading,
  Stack,
  Image,
} from '@chakra-ui/react'
import { Link, useNavigate } from 'react-router-dom';
import CreateCustomerForm from "../shared/CreateCustomerForm";
import { useAuth } from '../context/AuthContext';
import { useEffect } from 'react';


const Signup = () => {
  
  // const navigate = useNavigate();
  
  // let token = localStorage.getItem('access_token');
  //   if(token) {
  //       navigate('/dashboard');
  //   }

  const navigate = useNavigate();
  const { setCustomerFromToken } = useAuth();
  
    return (   
      <Stack minH={'100vh'} direction={{ base: 'column', md: 'row' }}>
        {/* <Flex p={8} flex={1} align={'center'} justify={'center'} justifyContent={"center"} flexDirection={'column'}> */}
        <Flex p={8} flex={1} alignItems={'center'} justifyContent={'center'}>
          <Stack spacing={4} w={'full'} maxW={'md'}>
            <Image src='https://img.freepik.com/free-vector/bird-colorful-logo-gradient-vector_343694-1365.jpg?size=338&ext=jpg&ga=GA1.1.87170709.1707523200&semt=ais' boxSize={"200px"} alt={"Aurick Logo"} />
            <Heading fontSize={'2xl'} mb={15}>Register for an account</Heading>
            <CreateCustomerForm onSuccess={(res) => {
              const jwtToken = res.headers.authorization;
              localStorage.setItem("access_token", jwtToken);
              setCustomerFromToken();
              navigate('/dashboard/customers');
            }}/>
            <Link color={'blue.500'} to={'/'}>
            Have an account? Login now.
          </Link>
          </Stack>
        </Flex>
        <Flex flex={1} padding={10} flexDirection={"column"} alignItems={"center"} justifyContent={"center"} bgGradient={{sm: 'linear(to-r, blue.600, purple.600)'}}>
          <Text fontSize={"6xl"} color={"white"} fontWeight={"bold"} mb={5}>
              <Link href={"https://google.com"}>
                  Enroll Now
              </Link>
          </Text>
          <Image
            alt={'Login Image'}
            objectFit={'scale-down'}
            src={
              'https://images.unsplash.com/photo-1486312338219-ce68d2c6f44d?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1352&q=80'
            }
          />
        </Flex>
      </Stack>
    )
}

export default Signup;