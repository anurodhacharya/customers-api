import { Wrap, WrapItem, Spinner, Text } from '@chakra-ui/react';
import SidebarWithHeader from './components/shared/SideBar';
import CardWithImage from './components/shared/Card';
import { useEffect, useState } from 'react';
import { getCustomers } from './services/client';

const App = () => {

  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    setLoading(true);
    getCustomers().then(res => {
      console.log(res.data);
      setCustomers(res.data);
    }).catch(err => {
        console.log(err);
    }).finally(() => {
        setLoading(false);
    })
  }, [])

  if(loading) {
    // console.log("Inside if loading is true")
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

  if(customers.length <= 0) {
    return (
      <SidebarWithHeader>
        <Text>No Customers Available</Text>
        {/* { console.log("Length less than 0")} */}
      </SidebarWithHeader>
    )
  }

  return (
    <SidebarWithHeader>
      <Wrap justify={"center"} spacing={"30px"}>
        { customers.map((customer, index) => {
          return (
            <WrapItem key={index}>
              <CardWithImage {...customer}></CardWithImage>
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