'use client'

import {
  Button,
  AlertIcon,
  Flex,
  Text,
  Alert,
  FormLabel,
  Heading,
  Input,
  Stack,
  Image,
} from '@chakra-ui/react'
import { Link, useNavigate } from 'react-router-dom';

import { Formik, Form, useField } from 'formik';
import * as Yup from 'yup';
// import { login } from '../../services/client';
import { useAuth } from '../context/AuthContext';
import { errorNotification } from '../../services/notification';
import { useEffect } from 'react';

const MyTextInput = ({ label, ...props }) => {
  const [field, meta] = useField(props);
  return (
    <>
      {/* {console.log("Inside the text input")} */}
      <FormLabel htmlFor={props.id || props.name}>{label}</FormLabel>
      <Input className="text-input" {...field} {...props} />
      {meta.touched && meta.error ? (
        <Alert className="error" status={"error"} mt={2}><AlertIcon></AlertIcon>{meta.error}</Alert>
      ) : null}
    </>
  );
};

const LoginForm = () => {
  const {user, login} = useAuth();
  const navigate =  useNavigate();

  return (
    <Formik 
      validateOnMount={true}
      validationSchema={
        Yup.object({
          username: Yup.string().email().required(),
          password: Yup.string().max(30).required(),
        })
      }
      initialValues={{username: '', password: ''}}
      onSubmit={(values, { setSubmitting }) => {
            setSubmitting(true);
            login(values).then(res => {
              navigate('/dashboard');
              console.log("Successfully logged in");
        }).catch(err => {
            errorNotification(err.code, err.response.data.message)
        }).finally(() => {
            setSubmitting(false);
        });
      }}
    >
      {({isValid, isSubmitting}) => (
          <Form>
            <Stack spacing={15}>
              {/* {console.log("Inside the form")} */}
              <MyTextInput label={"Email"} name={"username"} type={"email"} placeholder={"hello@aurick.com"} />
              <MyTextInput label={"Password"} name={"password"} type={"password"} placeholder={"type your password"} />
              <Button type="submit" isDisabled={!isValid || isSubmitting}>Login</Button>
            </Stack>
          </Form>
      )}

    </Formik>
  )
}

const Login = () => {  

  const { customer } = useAuth();
  const navigate = useNavigate();

  
  useEffect(() => {
    if(customer) {
      navigate('/dashboard');
    }
  })

  console.log("Before navigate inside Login");

  return (
    <Stack minH={'100vh'} direction={{ base: 'column', md: 'row' }}>
      {/* <Flex p={8} flex={1} align={'center'} justify={'center'} justifyContent={"center"} flexDirection={'column'}> */}
      <Flex p={8} flex={1} alignItems={'center'} justifyContent={'center'}>
        <Stack spacing={4} w={'full'} maxW={'md'}>
          <Image src='https://img.freepik.com/free-vector/bird-colorful-logo-gradient-vector_343694-1365.jpg?size=338&ext=jpg&ga=GA1.1.87170709.1707523200&semt=ais' boxSize={"200px"} alt={"Aurick Logo"} />
          <Heading fontSize={'2xl'} mb={15}>Sign in to your account</Heading>
          <LoginForm />
          <Link color={'blue.500'} to={'/signup'}>
            Don't have an accont? Sign up now.
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

export default Login;