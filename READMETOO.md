# Challenge Event Store

> The implementation should be correct, fast, memory-efficient, and
> thread-safe. You may consider that insertions, deletions, queries, and
> iterations will happen frequently and concurrently. This will be a
> system hotspot. Optimize at will.

With those requirements in mind I had to choose the right data structure to fulfil them. Honestly I don't have much experience and knowledge about data structures so this was a chance to do some studying. I searched for thread-safe data structures in Java and after some reading I ran into concurrent collections. I narrowed it down to `ConcurrentHashMap` and `CopyOnWriteArrayList`. I ended up choosing ConcurrentHashMap because CopyOnWriteArrayList have to create a copy of the entire ArrayList, making it expensive to use specially if I don't know how big the dataset can be. ConcurrentHashMap has good performance when adding, removing and checking if an element exists (O(1)) but to use it I had to give an `Id` attribute to the Event class, as ConcurrentHashMap uses a key-value pair to store entries.

## Implementing the store
The `EventStoreImpl` class itself was straightforward as I only needed three methods, but later on while doing tests I decided to have methods to return the size of the `EventStore` and also check if it contains an specific element as they would help me write better tests.

## Implementing the iterator

The iterator was also simple but while testing I found the need to use the `EventStore` remove method, so when creating the iterator I pass it an `EventStore` instance.

## Testing

Testing was the most difficult part since I don't have much experience with it, I tried some simple tests such as inserting, deleting and querying an element but had some problems with the testing concurrency and thread-safety. I think I may have a gap in knowlegde about multithreading to see if the problem was with my tests or if they weren't possible in the first place.

## What is missing
More tests. I think I haven't tested much, in part because of time but also experience since sometimes I didn't know what to test for.

## Conclusion
I think this was a good experience, it was one of the most interesting challenges I have done in a while. I think this is because I have more experience with frontend development so I didn't have much exposure to concerns about data structures and multithreading.


