# BoggleSolver
A solver implementation for the classic Boggle word game! Ever competed in an intense game of Boggle thinking you've exhausted all possible words on the board? Think no more!

## Usage
Given a dictionary file of your choosing and a board with custom dimensions, you'll have all the English words that exist within your Boggle board in no time! (On my computer the algorithm runs in < 3s on an 50x50 board!!) If you'd like to use a dictionary other than the one I've provided that's no problem- just make sure each word is separated by a newline.

Once navigated to the root project directory, use the following commands in the terminal:

**Mac OS X**
`gradlew -PmainClass=Boggle solve`

**Windows**
`gradlew.bat -PmainClass=Boggle solve`

Assuming the Boggle.java file hasn't been modified, this program will find all words contained in the following board:

```
Solving:

a r m
b e s
n i m

arsine
rabies
bismer
bersim
barmen
miners
minbar
abime
abies
abner
resin
bines
bisme
biers
benim
besin
besra
berms
bears
bearm
braes
barse
bares
barms
smear
serab
smear
nears
inerm
mines
miner
miser
arse
aren
ares
arms
rems
reim
rein
reis
rems
rabi
mems
mein
mear
mrem
bine
bise
bien
bier
beni
berm
bear
brei
bren
bars
bare
barm
ears
sime
sine
sier
semi
serb
sera
sear
nims
nies
near
inbe
mise
mien
mems
mear
aes
aer
abn
abr
ars
are
arb
arm
rem
rei
ren
res
reb
rem
rea
rms
rab
mem
men
mer
mea
mrs
bim
bin
bis
ben
bes
ber
bra
bae
bar
ems
ems
ers
era
ear
sim
sin
sie
sib
sem
sei
sen
sem
ser
sea
nim
nis
nib
nei
neb
nea
ism
ise
ism
iba
min
mis
mib
men
mem
mer
mea
```

# How does it work?

As you can imagine, looking up in a dictionary to check if every single possible sequence of letters is an english word would be quite an arduous task. It turns out that for any board of size *n x n*, there are a maximum of *n^2 x (n^2)!* possible sequences of letters that need to be checked for validity. This clearly breaks down very fast- a simple *3 x 3* board requires at most 3265920 validations, and this isn't even the size of a real Boggle board! So how did I do it? I mean, even solving an *8 x 8* board would take a maximum of roughly *10^90* computations! I must've done something really clever here. It turns out there exists an incredibly more efficient approach. The crux of the solving algorithm revolves around a particular data structure known as a Trie. Given a string of length *n*, a Trie is able to tell us whether or not the string we are searching for exists in our dictionary in *O(n)* (linear) time! This is a huge improvement over a binary search for each possible word in the dictionary file. In fact, a Trie can check whether or not adding a letter to an existing string will result in another valid word in constant time! This is what makes the algorithm as fast as it is: we perform a depth first search from each letter on the board, adding an adjacent letter to the current word we've built up and append it to our results if it yields a word contained in our dictionary. Pretty cool stuff!

The runtime efficiency comes at a cost however. The dictionary file I use is roughly 5 megabytes of text, so the space requirements for compiling every word into a dictionary Trie is quite enormous. Consctructing a Trie from the dictionary time is actually what takes the longest to accomplish, with on average ~2.2 second execution time. If we look on the bright side, the creation of the dictionary Trie requires constant time and space with respect to the dimensions of the board we are solving. It follows that the only element of concern is the runtime of our solving algorithm, which has proven to be efficient in solving boards with dimensions of up to *50 x 50* in less than 3 seconds. If you're interesting in the extreme bounds, it took the algorithm around 77 seconds to find solutions to a *250 x 250* board.
