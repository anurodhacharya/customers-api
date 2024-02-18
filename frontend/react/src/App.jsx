import { Wrap, WrapItem, Spinner, Text } from '@chakra-ui/react';
import SidebarWithHeader from './components/shared/SideBar';
import CardWithImage from './components/Card';
import { useEffect, useState } from 'react';
import { getCustomers } from './services/client';
import DrawerForm from './components/customer/CreateCustomerDrawer';
import { errorNotification } from './services/notification';

const App = () => {

  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);

  const fetchCustomers = () => {
    console.log("Before setting loading to true");
    setLoading(true);
    console.log("After setting loading to true");
    getCustomers().then(res => {
      console.log(res.data);
      console.log("Inside then function")
      setCustomers(res.data);
      console.log("After setting customers")
    }).catch(err => {
        console.log(err);
        setError(true);
        errorNotification(err.code, err.response.data.message);
    }).finally(() => {
        setLoading(false);
        console.log("loading set to false");
    });

    console.log("Last line of fetchCustomers");
  }

  useEffect(() => {
    fetchCustomers();
  }, [])

  if(loading) {
    console.log("Inside if loading is true")
    return ( 
      <SidebarWithHeader>
        <Spinner
          thickness='4px'
          speed='0.65s'
          emptyColor='gray.200'
          color='blue.500'
          size='xl'
        />
      </SidebarWithHeader>
    )
  }

  if(error) {
    return (
      <SidebarWithHeader>
        {/* <DrawerForm fetchCustomers={fetchCustomers}></DrawerForm> */}
        <DrawerForm></DrawerForm>
        <Text mt={5}>There was an error</Text>
      </SidebarWithHeader>
    )
  }

  if(customers.length <= 0) {
    return (
      <SidebarWithHeader>
        {/* <DrawerForm fetchCustomers={fetchCustomers}></DrawerForm> */}
        <DrawerForm></DrawerForm>
        <Text mt={5}>No Customers Available</Text>
        { console.log("Length less than 0")}
      </SidebarWithHeader>
    )
  }

  return (
    <SidebarWithHeader>
      <DrawerForm fetchCustomers={fetchCustomers}></DrawerForm>
      {console.log('Inside main return')}
      <Wrap justify={"center"} spacing={"30px"}>
        { customers.map((customer, index) => {
          return (
            <WrapItem key={index}>
              <CardWithImage {...customer} fetchCustomers={fetchCustomers}></CardWithImage>
            </WrapItem>
          )
        }) }
      </Wrap>
    </SidebarWithHeader>
  )


  /*
  return (
    <SidebarWithHeader>
      <Wrap>
        { customers.map((customer, index) => {
          <WrapItem>
          return (
            <CardWithImage>{ customer.name }</CardWithImage>
          )
          </WrapItem>
        }) }
      </Wrap>
    </SidebarWithHeader>
  )*/
}

export default App;