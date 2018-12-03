#include <iostream>
#include <fstream>
#include <algorithm>

int main(int argc, char* argv[]) {
  char letters[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                     'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                     'w', 'x', 'y', 'z' };

  std::ifstream fs(argv[1], std::ifstream::in);
  std::string line;
  int two_letters = 0;
  int three_letters = 0;
  bool already_seen_two = false;
  bool already_seen_three = false;

  while(std::getline(fs, line)) {
    for (auto letter : letters) {
      auto ocurrences = std::count(line.begin(), line.end(), letter);

      if (ocurrences == 2 && !already_seen_two) {
        two_letters++;
        already_seen_two = true;
      }

      if (ocurrences == 3 && !already_seen_three) {
        three_letters++;
        already_seen_three = true;
      }
    }

    already_seen_two = false;
    already_seen_three = false;
  }

  std::cout << "Result " << two_letters * three_letters << std::endl;

  fs.close();
  return EXIT_SUCCESS;
}
