#include <iostream>
#include <fstream>
#include <unordered_set>

int main(int argc, char* argv[]) {
  std::ifstream fs(argv[1], std::ifstream::in);
  std::string line;
  std::unordered_set<int> frequencies;
  int total_frequency = 0;
  bool found = false;

  frequencies.insert(total_frequency);

  while(!found) {
    while(std::getline(fs, line)) {
      auto num = std::stoi(line);
      total_frequency += num;

      if (frequencies.count(total_frequency) == 0) {
        frequencies.insert(total_frequency);
      } else {
        found = true;
        std::cout << "Found double frequency! " << total_frequency << std::endl;
        break;
      }
    }

    fs.clear();
    fs.seekg(0, std::ios_base::beg);
  }

  fs.close();
  return EXIT_SUCCESS;
}
